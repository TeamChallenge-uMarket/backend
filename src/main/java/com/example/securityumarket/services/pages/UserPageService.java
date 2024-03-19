package com.example.securityumarket.services.pages;

import com.example.securityumarket.dto.entities.user.UserDetailsDTO;
import com.example.securityumarket.dto.entities.user.UserSecurityDetailsDTO;
import com.example.securityumarket.dto.pages.user.request.RequestUpdateTransportDetails;
import com.example.securityumarket.dto.pages.user.response.CountTransportByStatusResponse;
import com.example.securityumarket.dto.pages.user.response.TransportByStatusResponse;
import com.example.securityumarket.dto.transports.TransportDTO;
import com.example.securityumarket.exception.BadRequestException;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.exception.DataNotValidException;
import com.example.securityumarket.exception.EmailSendingException;
import com.example.securityumarket.models.City;
import com.example.securityumarket.models.DriveType;
import com.example.securityumarket.models.FuelType;
import com.example.securityumarket.models.NumberAxles;
import com.example.securityumarket.models.ProducingCountry;
import com.example.securityumarket.models.Transmission;
import com.example.securityumarket.models.Transport;
import com.example.securityumarket.models.TransportColor;
import com.example.securityumarket.models.TransportCondition;
import com.example.securityumarket.models.TransportGallery;
import com.example.securityumarket.models.Users;
import com.example.securityumarket.models.WheelConfiguration;
import com.example.securityumarket.services.jpa.BodyTypeService;
import com.example.securityumarket.services.jpa.CityService;
import com.example.securityumarket.services.jpa.DriveTypeService;
import com.example.securityumarket.services.jpa.FuelTypeService;
import com.example.securityumarket.services.jpa.NumberAxlesService;
import com.example.securityumarket.services.jpa.ProducingCountryService;
import com.example.securityumarket.services.jpa.TransmissionService;
import com.example.securityumarket.services.jpa.TransportColorService;
import com.example.securityumarket.services.jpa.TransportConditionService;
import com.example.securityumarket.services.jpa.TransportGalleryService;
import com.example.securityumarket.services.jpa.TransportService;
import com.example.securityumarket.services.jpa.UserService;
import com.example.securityumarket.services.jpa.WheelConfigurationService;
import com.example.securityumarket.services.redis.FilterParametersService;
import com.example.securityumarket.services.security.JwtService;
import com.example.securityumarket.services.storage.CloudinaryService;
import com.example.securityumarket.util.EmailUtil;
import com.example.securityumarket.util.converter.transposrt_type.LoadBearingVehicleConversionStrategy;
import com.example.securityumarket.util.converter.transposrt_type.MotorizedFourWheeledVehicleConversionStrategy;
import com.example.securityumarket.util.converter.transposrt_type.MotorizedVehicleConversionStrategy;
import com.example.securityumarket.util.converter.transposrt_type.TransportConverter;
import com.example.securityumarket.util.converter.transposrt_type.WaterVehicleConversionStrategy;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.example.securityumarket.dao.specifications.TransportSpecifications.findByUser;
import static com.example.securityumarket.dao.specifications.TransportSpecifications.hasStatus;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserPageService {

    private final SubscriptionPageService subscriptionPageService;

    private final UserService userService;

    private final CityService cityService;

    private final CloudinaryService cloudinaryService;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final TransportService transportService;

    private final TransportConverter transportConverter;

    private final TransportGalleryService transportGalleryService;

    private final BodyTypeService bodyTypeService;

    private final TransmissionService transmissionService;

    private final FuelTypeService fuelTypeService;

    private final DriveTypeService driveTypeService;

    private final NumberAxlesService numberAxlesService;

    private final TransportColorService transportColorService;

    private final TransportConditionService transportConditionService;

    private final ProducingCountryService producingCountryService;

    private final WheelConfigurationService wheelConfigurationService;

    private final EmailUtil emailUtil;

    private final FilterParametersService filterParametersService;


    @Value("${cloudinary.default.not-found-photo}")
    private String defaultPhoto;


    public UserDetailsDTO getUserDetails() {
        Users user = userService.getAuthenticatedUser();
        return buildUserDetailsDTOFromUser(user);
    }

    private UserDetailsDTO buildUserDetailsDTOFromUser(Users user) {
        UserDetailsDTO dto = new UserDetailsDTO();
        fillUserDetailsDTO(dto, user);
        return dto;
    }

    public void fillUserDetailsDTO(UserDetailsDTO dto, Users user) {
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setCityId((user.getCity() != null) ? user.getCity().getId() : null);
        dto.setPhone(user.getPhone());
        dto.setPhotoUrl(user.getPhotoUrl());
    }


    @Transactional
    public void updateUserDetails(UserDetailsDTO userDetailsDTO,
                                  MultipartFile multipartFile) {
        Users currentUser = userService.getAuthenticatedUser();

        updateUserFields(userDetailsDTO, multipartFile, currentUser);

        jwtService.generateToken(currentUser);
        String refreshToken = jwtService.generateRefreshToken(currentUser);
        currentUser.setRefreshToken(refreshToken);

        userService.save(currentUser);
        log.info("User details updated successfully for user with ID {}.", currentUser.getId());
    }

    @Transactional
    public void deleteGalleryFiles(List<Long> galleryId) {
        galleryId.forEach(id -> {
                    TransportGallery gallery = transportGalleryService.findById(id);
                    try {
                        cloudinaryService.deleteFile(gallery.getImageName());
                        log.info("Image '{}' deleted from Cloudinary.", gallery.getImageName());
                    } catch (Exception e) {
                        log.error("Failed to delete image '{}' from Cloudinary: {}", gallery.getImageName(), e.getMessage(), e);
                    }
                    transportGalleryService.deleteTransportGalleryById(gallery.getId());
                    log.info("Gallery entry with ID {} deleted successfully.", gallery.getId());
                }
        );
    }


    public void deleteUserPhoto() {
        Users authenticatedUser = userService.getAuthenticatedUser();

        if (!authenticatedUser.getPhotoUrl().equals(defaultPhoto)) { //TODO
            String photoUrl = authenticatedUser.getPhotoUrl();
            cloudinaryService.deleteFile(CloudinaryService
                    .getPhotoPublicIdFromUrl(photoUrl));
            authenticatedUser.setPhotoUrl(defaultPhoto);
            userService.save(authenticatedUser);

            log.info("User photo deleted successfully for user with ID {}.", authenticatedUser.getId());
        }
    }

    public void updateSecurityInformation(UserSecurityDetailsDTO securityDetailsDTO) {
        Users currentUser = userService.getAuthenticatedUser();

        if (currentUser.getPassword() != null) {
            if (!passwordEncoder.matches(securityDetailsDTO.getOldPassword(),
                    currentUser.getPassword())) {
                throw new DataNotValidException("The old password is incorrect");
            }
        }
        currentUser.setPassword(passwordEncoder.encode(securityDetailsDTO.getPassword()));
        userService.save(currentUser);

        try {
            emailUtil.sendNotificationEmail(currentUser.getEmail(), "Your password has been successfully changed.",
                    "Password change detected. If that wasn't you, reset password immediately");
        } catch (MessagingException e) {
            throw new EmailSendingException();
        }

        log.info("User security details updated successfully for user with ID {}.", currentUser.getId());
    }

    public List<TransportByStatusResponse> getMyTransportsByStatus(String status) {
        Users authenticatedUser = userService.getAuthenticatedUser();
        try {
            Transport.Status transportStatus = Transport.Status.valueOf(status.toUpperCase());
            List<Transport> transportByUserAndStatus =
                    findTransportByUserAndStatus(authenticatedUser, transportStatus);

            return convertTransportListToTransportByStatusResponse(transportByUserAndStatus);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid status");
        }
    }

    public List<Transport> findTransportByUserAndStatus(Users user, Transport.Status status) {
        Specification<Transport> specification = findByUser(user)
                .and(hasStatus(status));
        return transportService.findAll(specification);
    }

    public List<Transport> findTransportByUser(Users user) {
        Specification<Transport> specification = findByUser(user);
        return transportService.findAll(specification);
    }

    public List<TransportByStatusResponse> convertTransportListToTransportByStatusResponse(
            List<Transport> transports) {
        return transports.stream()
                .map(transportConverter::convertTransportToTransportByStatusResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateTransportStatus(Long transportId, String status) {
        try {
            Transport transportById = transportService.findTransportById(transportId);
            Transport.Status transportStatus = Transport.Status.valueOf(status.toUpperCase());

            updateStatusByTransportIdAndStatus(transportById, transportStatus);
//            Users authenticatedUser = userService.getAuthenticatedUser();
//            if (isUserHasAdminOrModeratorRole(authenticatedUser)) { TODO: For future
//                updateStatusByTransportIdAndStatus(transportById, transportStatus);
//            } else if (transportStatus.equals(Transport.Status.ACTIVE)) {
//                updateStatusByTransportIdAndStatus(transportById, Transport.Status.PENDING);
//            } else if (isUserHasUserRole(authenticatedUser)) {
//                updateStatusByTransportIdAndStatus(transportById, transportStatus);
//            } else {
//                throw new InsufficientPermissionsException("to update the transport status");
//            }
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid status");
        }
    }

    @Transactional
    public void updateTransportDetails(
            Long transportId,
            @Valid RequestUpdateTransportDetails updateTransportDetails,
            MultipartFile[] multipartFiles) {
        Transport transport = transportService.findTransportById(transportId);

        if (updateTransportDetails != null) {
            updateTransportFields(updateTransportDetails, transport);
            log.info("Updated transport fields with ID {} for User with ID {}.",
                    transportId, userService.getAuthenticatedUser());
        }

        if (multipartFiles != null) {
            updateFieldIfPresent(multipartFiles, files -> {
                if (updateTransportDetails != null) {
                    transportGalleryService.uploadFiles(
                            files, updateTransportDetails.getMainPhoto(), transport);
                } else {
                    transportGalleryService.uploadFiles(
                            files, null, transport);
                    log.info("Uploaded {} files to transport gallery.", multipartFiles.length);
                }
            });
        }

        if (transport.getStatus().equals(Transport.Status.ACTIVE)) {
            transport.setStatus(Transport.Status.PENDING);
            log.info("Changed transport status to PENDING.");
        }

        transportService.save(transport);

        filterParametersService.update(transport);

        log.info("Transport with ID {} updated successfully.", transportId);
    }


    public void updateStatusByTransportIdAndStatus(Transport transport,
                                                   Transport.Status status) {
        transport.setStatus(status);
        if (!status.equals(Transport.Status.ACTIVE)) {
            subscriptionPageService.removeTransportFromSubscription(transport);
        }

// TODO FOR FUTURE
//        if (status.equals(Transport.Status.ACTIVE)) {
//            subscriptionPageService.notifyUsers(transport);
//        } else {
//            subscriptionPageService.removeTransportFromSubscription(transport);
//        }

        transportService.save(transport);

        log.info("Transport with ID {} updated successfully to status {} for user with ID {}.",
                transport.getId(), status, userService.getAuthenticatedUser().getId());
    }

    private boolean isUserHasAdminOrModeratorRole(Users authenticatedUser) {
        return authenticatedUser.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN")
                        || authority.getAuthority().equals("MODERATOR"));
    }

    private boolean isUserHasUserRole(Users authenticatedUser) {
        return authenticatedUser.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("USER"));
    }

    public ResponseEntity<? extends TransportDTO> getTransportDetails(Long transportId) {
        Transport transport = transportService.findTransportById(transportId);
        return convertTransportToTypeDTO(transport);
    }

    private ResponseEntity<? extends TransportDTO> convertTransportToTypeDTO(Transport transport) {
        Long transportTypeId = transport.getTransportModel()
                .getTransportTypeBrand()
                .getTransportType()
                .getId();

        return switch (transportTypeId.intValue()) {
            case 1 -> ResponseEntity.ok(transportConverter.convertTransportToTypeDTO(
                    transport, new MotorizedFourWheeledVehicleConversionStrategy()));
            case 2 -> ResponseEntity.ok(transportConverter.convertTransportToTypeDTO(
                    transport, new MotorizedVehicleConversionStrategy()));
            case 3, 4, 5 -> ResponseEntity.ok(transportConverter.convertTransportToTypeDTO(
                    transport, new LoadBearingVehicleConversionStrategy()));
            case 6 -> ResponseEntity.ok(transportConverter.convertTransportToTypeDTO(
                    transport, new WaterVehicleConversionStrategy()));
            default -> throw new DataNotFoundException("Transport type id");
        };
    }

    private void updateTransportFields(
            RequestUpdateTransportDetails transportDetailsDTO, Transport currentTransport) {
        updateFieldIfPresent(transportDetailsDTO.getYear(),
                currentTransport::setYear);

        updateFieldIfPresent(transportDetailsDTO.getMileage(),
                currentTransport::setMileage);

        updateFieldIfPresent(transportDetailsDTO.getPrice(),
                currentTransport::setPrice);

        updateFieldIfPresent(transportDetailsDTO.getBargain(),
                currentTransport::setBargain);

        updateFieldIfPresent(transportDetailsDTO.getTrade(),
                currentTransport::setTrade);

        updateFieldIfPresent(transportDetailsDTO.getMilitary(),
                currentTransport::setMilitary);

        updateFieldIfPresent(transportDetailsDTO.getInstallmentPayment(),
                currentTransport::setInstallmentPayment);

        updateFieldIfPresent(transportDetailsDTO.getUncleared(),
                currentTransport::setUncleared);

        updateFieldIfPresent(transportDetailsDTO.getLoadCapacity(),
                currentTransport::setLoadCapacity);

        updateFieldIfPresent(transportDetailsDTO.getAccidentHistory(),
                currentTransport::setAccidentHistory);

        updateFieldIfPresent(transportDetailsDTO.getNumberOfDoors(),
                currentTransport::setNumberOfDoors);

        updateFieldIfPresent(transportDetailsDTO.getNumberOfSeats(),
                currentTransport::setNumberOfSeats);

        updateFieldIfPresent(transportDetailsDTO.getConsumptionCity(),
                currentTransport::setFuelConsumptionCity);

        updateFieldIfPresent(transportDetailsDTO.getConsumptionHighway(),
                currentTransport::setFuelConsumptionHighway);

        updateFieldIfPresent(transportDetailsDTO.getConsumptionMixed(),
                currentTransport::setFuelConsumptionMixed);

        updateFieldIfPresent(transportDetailsDTO.getEngineDisplacement(),
                currentTransport::setEngineDisplacement);

        updateFieldIfPresent(transportDetailsDTO.getEnginePower(),
                currentTransport::setEnginePower);

        updateFieldIfPresent(transportDetailsDTO.getVincode(),
                currentTransport::setVincode);

        updateFieldIfPresent(transportDetailsDTO.getDescription(),
                currentTransport::setDescription);

        updateFieldIfPresent(transportDetailsDTO.getPhone(), phone -> {
            String normalizePhoneNumber = normalizePhoneNumber(phone);
            currentTransport.setPhone(normalizePhoneNumber);
        });

        updateFieldIfPresent(transportDetailsDTO.getBodyType(), bodyType -> {
            bodyTypeService.findById(bodyType);
            currentTransport.setId(bodyType);
        });

        updateFieldIfPresent(transportDetailsDTO.getCity(), cityId -> {
            City city = cityService.findById(cityId);
            currentTransport.setCity(city);
        });

        updateFieldIfPresent(transportDetailsDTO.getTransmission(), transmissionId -> {
            Transmission transmission = transmissionService.findById(transmissionId);
            currentTransport.setTransmission(transmission);
        });

        updateFieldIfPresent(transportDetailsDTO.getFuelType(), fuelTypeId -> {
            FuelType fuelType = fuelTypeService.findById(fuelTypeId);
            currentTransport.setFuelType(fuelType);
        });

        updateFieldIfPresent(transportDetailsDTO.getDriveType(), driveTypeId -> {
            DriveType driveType = driveTypeService.findById(driveTypeId);
            currentTransport.setDriveType(driveType);
        });

        updateFieldIfPresent(transportDetailsDTO.getNumberAxles(), numberAxlesId -> {
            NumberAxles numberAxles = numberAxlesService.findById(numberAxlesId);
            currentTransport.setNumberAxles(numberAxles);
        });

        updateFieldIfPresent(transportDetailsDTO.getColor(), colorId -> {
            TransportColor color = transportColorService.findById(colorId);
            currentTransport.setTransportColor(color);
        });

        updateFieldIfPresent(transportDetailsDTO.getCondition(), conditionId -> {
            TransportCondition condition = transportConditionService.findById(conditionId);
            currentTransport.setTransportCondition(condition);
        });

        updateFieldIfPresent(transportDetailsDTO.getProducingCountry(), countryId -> {
            ProducingCountry country = producingCountryService.findById(countryId);
            currentTransport.setProducingCountry(country);
        });

        updateFieldIfPresent(transportDetailsDTO.getWheelConfiguration(), wheelConfigurationId -> {
            WheelConfiguration wheelConfiguration = wheelConfigurationService
                    .findById(wheelConfigurationId);
            currentTransport.setWheelConfiguration(wheelConfiguration);
        });
    }


    private void updateUserFields(UserDetailsDTO userDetailsDTO,
                                  MultipartFile photo, Users currentUser) {
        updateFieldIfPresent(userDetailsDTO.getName(), currentUser::setName);
        updateFieldIfPresent(userDetailsDTO.getPhone(), phone -> {
            String normalizePhoneNumber = normalizePhoneNumber(phone);
            userService.existsUsersByPhone(normalizePhoneNumber);
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

            if (!oldUrl.equals(defaultPhoto)) {
                cloudinaryService.deleteFile(CloudinaryService.getPhotoPublicIdFromUrl(oldUrl));
            }
        });

        updateFieldIfPresent(userDetailsDTO.getEmail(), email -> {
            userService.isUserEmailUnique(email);
            currentUser.setEmail(email);
        });
    }

    private <T> void updateFieldIfPresent(T newValue, Consumer<T> updateFunction) {
        Optional.ofNullable(newValue)
                .ifPresent(updateFunction);
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

    private String uploadUserPhoto(MultipartFile photo) {
        String fileName = cloudinaryService.uploadFileWithPublicRead(photo);
        return cloudinaryService.getOriginalUrl(fileName);
    }

    public List<CountTransportByStatusResponse> countTransports() {
        Users authenticatedUser = userService.getAuthenticatedUser();
        List<Transport> transportByUserAndStatus = findTransportByUser(authenticatedUser);
        Map<Transport.Status, Long> countByStatus = transportByUserAndStatus.stream()
                .collect(Collectors.groupingBy(Transport::getStatus, Collectors.counting()));

        return countByStatus.entrySet().stream()
                .map(entry -> new CountTransportByStatusResponse(entry.getKey(), entry.getValue()))
                .toList();
    }
}
