package com.example.securityumarket.services.pages;

import com.example.securityumarket.exception.DataNotValidException;
import com.example.securityumarket.exception.EmailSendingException;
import com.example.securityumarket.dto.pages.login.RegisterRequest;
import com.example.securityumarket.models.Role;
import com.example.securityumarket.models.Users;
import com.example.securityumarket.services.jpa.RoleService;
import com.example.securityumarket.services.jpa.UserRoleService;
import com.example.securityumarket.services.jpa.UserService;
import com.example.securityumarket.services.security.JwtService;
import com.example.securityumarket.util.EmailUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class RegistrationPageService {

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    private final JwtService jwtService;

    private final EmailUtil emailUtil;

    private final UserRoleService userRoleService;

    private final RoleService roleService;


    @Value("${cloudinary.default.not-found-photo}")
    private String defaultPhoto;


    @Transactional
    public void register(RegisterRequest registerRequest) {
        validateRegisterRequest(registerRequest);
        Users user = buildUserFromRequest(registerRequest);

        Role role = roleService.findRoleByName(Role.Roles.USER);
        userRoleService.addUseRole(user, role);
        userService.save(user);

        sendEmailAndSaveUser(user);

        log.info("User with email {} registered successfully.", user.getEmail());
    }

    public void resendCode(String email) {
        Users user = userService.findAppUserByEmail(email);
        if (user.isActive()) {
            throw new DataNotValidException("Account with this email: " +
                    email + "has already activated. You can login.");
        } else {
            sendEmailAndSaveUser(user);

            log.info("Activation code resent to user with email: {}", email);
        }
    }


    private void sendEmailAndSaveUser(Users user) {
        String email = user.getEmail();
        String token = jwtService.generateRefreshToken(user);

        try {
            emailUtil.sendRegistrationEmail(email, token);
        } catch (MessagingException e) {
            throw new EmailSendingException();
        }

        user.setRefreshToken(token);
        userService.save(user);
    }

    public void verifyAccount(String email, String token) {
        Users user = userService.findAppUserByEmail(email);
        if (emailUtil.verifyAccount(user, token)) {
            user.setActive(true);
            userService.save(user);

            log.info("User with email {} activated account successfully.", email);
        } else {
            throw new DataNotValidException
                    ("Token has expired. Please regenerate token and try again");
        }
    }


    private void validateRegisterRequest(RegisterRequest registerRequest) {
        userService.isUserEmailUnique(registerRequest.getEmail());
    }

    private Users buildUserFromRequest(RegisterRequest registerRequest) {
        return Users.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .active(false)
                .status(Users.Status.OFFLINE)
                .photoUrl(defaultPhoto)
                .build();
    }
}
