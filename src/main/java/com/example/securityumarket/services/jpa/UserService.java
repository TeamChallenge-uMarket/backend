package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.UsersDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.exception.DataNotValidException;
import com.example.securityumarket.exception.DuplicateDataException;
import com.example.securityumarket.exception.UnauthenticatedException;
import com.example.securityumarket.models.DTO.entities.user.UserDetailsDTO;
import com.example.securityumarket.models.DTO.entities.user.UserSecurityDetailsDTO;
import com.example.securityumarket.models.entities.City;
import com.example.securityumarket.models.entities.Transport;
import com.example.securityumarket.models.entities.Users;
import com.example.securityumarket.services.notification.Observer;
import com.example.securityumarket.services.security.JwtService;
import com.example.securityumarket.services.storage.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UsersDAO usersDAO;

    private final CityService cityService;

    private final JwtService jwtService;

    private final CloudinaryService cloudinaryService;

    private final PasswordEncoder passwordEncoder;


    @Value("${cloudinary.default.not-found-photo}")
    private String DEFAULT_PHOTO;


    @Value("${mail.code.expiration.time}")
    private long codeExpirationTimeMs;


    private String getAuthenticatedUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public Users getAuthenticatedUser() {
        String email = getAuthenticatedUserEmail();
        if (email.equals("anonymousUser")) {
            throw new UnauthenticatedException();
        }
        return findAppUserByEmail(email);
    }

    public Users findAppUserByEmail(String email) {
        return usersDAO.findAppUserByEmail(email)
            .orElseThrow(() -> new DataNotFoundException("User with email " + email));
    }

    public void save(Users user) {
        usersDAO.save(user);
    }

    public void saveAndFlush(Users user) {
        usersDAO.saveAndFlush(user);
    }

    public boolean existsUsersByEmail(String email) {
        return usersDAO.existsUsersByEmail(email);
    }

    public void existsUsersByPhone(String phone) {
        usersDAO.findUserByPhone(phone).ifPresent(users -> {
            throw new DuplicateDataException("User with " + phone + " already exists");
        });
    }

    public boolean isUserAuthenticated() {
        String authenticatedUserEmail = getAuthenticatedUserEmail();
        return !authenticatedUserEmail.equals("anonymousUser");
    }

    public void isUserEmailUnique(String email) {
        if (existsUsersByEmail(email)) {
            throw new DuplicateDataException("User with " + email + " already exists");
        }
    }

    @Transactional
    public ResponseEntity<String> updateUserDetails(UserDetailsDTO userDetailsDTO, MultipartFile photo) {
        Users currentUser = getAuthenticatedUser();
        updateUserFields(userDetailsDTO, photo, currentUser);

        jwtService.generateToken(currentUser);
        String refreshToken = jwtService.generateRefreshToken(currentUser);
        currentUser.setRefreshToken(refreshToken);
        usersDAO.save(currentUser);

        return ResponseEntity.ok("User details updated successfully");
    }

    public ResponseEntity<String> updateUserSecurityDetails(UserSecurityDetailsDTO securityDetailsDTO) {
        Users currentUser = getAuthenticatedUser();

        if (currentUser.getPassword() != null) {
            if (!passwordEncoder.matches(securityDetailsDTO.getOldPassword(), currentUser.getPassword())) {
                throw new DataNotValidException("The old password is incorrect");
            }
        }
        currentUser.setPassword(passwordEncoder.encode(securityDetailsDTO.getPassword()));
        usersDAO.save(currentUser);

        return ResponseEntity.ok("User password changed successfully");
    }

    @Scheduled(cron = "0 0/30 * * * *")
    @Transactional
    public void deleteInactiveUsers() {
        LocalDateTime thirtyMinutesAgo = LocalDateTime.now().minus(codeExpirationTimeMs, ChronoUnit.MILLIS);
        usersDAO.deleteByActiveFalseAndCreatedDateBefore(thirtyMinutesAgo);
    }


    private String uploadUserPhoto(MultipartFile photo) {
        String fileName = cloudinaryService.uploadFileWithPublicRead(photo);
        return cloudinaryService.getOriginalUrl(fileName);
    }

    private <T> void updateFieldIfPresent(T newValue, Consumer<T> updateFunction) {
        Optional.ofNullable(newValue)
                .ifPresent(updateFunction);
    }

    private void updateUserFields(UserDetailsDTO userDetailsDTO, MultipartFile photo, Users currentUser) {
        updateFieldIfPresent(userDetailsDTO.getName(), currentUser::setName);
        updateFieldIfPresent(userDetailsDTO.getPhone(), phone -> {
            String normalizePhoneNumber = normalizePhoneNumber(phone);
            existsUsersByPhone(normalizePhoneNumber);
            currentUser.setPhone(normalizePhoneNumber);
        });
        updateFieldIfPresent(userDetailsDTO.getCityId(), cityId -> {
            City city = cityService.findById(cityId);
            currentUser.setCity(city);
        });
        updateFieldIfPresent(photo, url -> {
            String oldUrl = currentUser.getPhotoUrl();

            String urlFile = uploadUserPhoto(photo);
            currentUser.setPhotoUrl(urlFile);

            if (!oldUrl.equals(DEFAULT_PHOTO)) {
                cloudinaryService.deleteFile(CloudinaryService.getPhotoPublicIdFromUrl(oldUrl));
            }
        });
        updateFieldIfPresent(userDetailsDTO.getEmail(), email -> {
            isUserEmailUnique(email);
            currentUser.setEmail(email);
        });
    }


    private String normalizePhoneNumber(String inputPhoneNumber) {
        String digitsAndParentheses = inputPhoneNumber.replaceAll("[^\\d()]", "");

        String digitsOnly = digitsAndParentheses.replaceAll("[()]", "");

        String normalizedNumber;
        if (digitsOnly.startsWith("38")) {
            normalizedNumber = "+" + digitsOnly;
        } else {
            normalizedNumber = "+38" + digitsOnly;
        }

        return normalizedNumber;
    }

    public ResponseEntity<String> deleteUserPhoto() {
        Users authenticatedUser = getAuthenticatedUser();
        if (!authenticatedUser.getPhotoUrl().equals(DEFAULT_PHOTO)) {
            String photoUrl = authenticatedUser.getPhotoUrl();
            cloudinaryService.deleteFile(CloudinaryService.getPhotoPublicIdFromUrl(photoUrl));
            authenticatedUser.setPhotoUrl(DEFAULT_PHOTO);
            save(authenticatedUser);
            return ResponseEntity.ok("The user photo has been deleted");
        } else {
            return ResponseEntity.ok("The user photo is already deleted");
        }
    }

    private static String extractFileNameFromUrl(String url) {
        try {
            URL imageUrl = new URL(url);
            Path path = Paths.get(imageUrl.getPath());
            String fileNameAndFormat = path.getFileName().toString();
            return fileNameAndFormat.substring(0, fileNameAndFormat.length()-4);
        } catch (Exception e) {
            throw new DataNotValidException("Url file is not valid");
        }
    }

    public void setUserStatusOnline(Users user) {
        user.setStatus(Users.Status.ONLINE);
        save(user);
    }

    public void setUserStatusOffline(Users user) {
        Users userByEmail = findAppUserByEmail(user.getEmail());
        userByEmail.setStatus(Users.Status.OFFLINE);
        save(userByEmail);
    }

    public List<Users> findAllByStatus(Users.Status status) {
        return usersDAO.findAllByStatus(status).orElse(Collections.emptyList());
    }
}