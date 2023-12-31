package com.example.securityumarket.services.pages;

import com.example.securityumarket.dto.entities.user.TransportPageUserDetailsDto;
import com.example.securityumarket.exception.BadRequestException;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.exception.DataNotValidException;
import com.example.securityumarket.exception.InsufficientPermissionsException;
import com.example.securityumarket.dto.entities.user.UserDetailsDTO;
import com.example.securityumarket.dto.entities.user.UserSecurityDetailsDTO;
import com.example.securityumarket.dto.pages.user.request.RequestUpdateTransportDetails;
import com.example.securityumarket.dto.pages.user.response.TransportByStatusResponse;
import com.example.securityumarket.dto.transports.TransportDTO;
import com.example.securityumarket.models.*;
import com.example.securityumarket.services.jpa.*;
import com.example.securityumarket.services.security.JwtService;
import com.example.securityumarket.services.storage.CloudinaryService;
import com.example.securityumarket.util.converter.transposrt_type.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.example.securityumarket.dao.specifications.TransportSpecifications.findByUser;
import static com.example.securityumarket.dao.specifications.TransportSpecifications.hasStatus;

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


    @Value("${cloudinary.default.not-found-photo}")
    private String defaultPhoto;


    public UserDetailsDTO getUserDetails() {
        Users user = userService.getAuthenticatedUser();
        return buildUserDetailsDTOFromUser(user);
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
    }

    @Transactional
    public void deleteGalleryFiles(List<Long> galleryId) {
        for (Long id : galleryId) {
            TransportGallery gallery = transportGalleryService.findById(id);
            cloudinaryService.deleteFile(gallery.getImageName());
            transportGalleryService.deleteTransportGalleryById(gallery.getId());
        }
    }


    public void deleteUserPhoto() {
        Users authenticatedUser = userService.getAuthenticatedUser();
        if (!authenticatedUser.getPhotoUrl().equals(defaultPhoto)) {
            String photoUrl = authenticatedUser.getPhotoUrl();
            cloudinaryService.deleteFile(CloudinaryService
                    .getPhotoPublicIdFromUrl(photoUrl));
            authenticatedUser.setPhotoUrl(defaultPhoto);
            userService.save(authenticatedUser);
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

    public List<TransportByStatusResponse> convertTransportListToTransportByStatusResponse(
            List<Transport> transports) {
        return transports.stream()
                .map(transportConverter::convertTransportToTransportByStatusResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateTransportStatus(Long transportId, String status) {
        try {
            Users authenticatedUser = userService.getAuthenticatedUser();
            Transport transportById = transportService.findTransportById(transportId);
            Transport.Status transportStatus = Transport.Status.valueOf(status.toUpperCase());

            if (isUserHasAdminOrModeratorRole(authenticatedUser)) {
                updateStatusByTransportIdAndStatus(transportById, transportStatus);
            } else if (transportStatus.equals(Transport.Status.ACTIVE)) {
                updateStatusByTransportIdAndStatus(transportById, Transport.Status.PENDING);
            } else if (isUserHasUserRole(authenticatedUser)) {
                updateStatusByTransportIdAndStatus(transportById, transportStatus);
            } else {
                throw new InsufficientPermissionsException("to update the transport status");
            }
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
        }

        if (multipartFiles != null) {
            updateFieldIfPresent(multipartFiles, files -> {
                if (updateTransportDetails != null) {
                    transportGalleryService.uploadFiles(
                            files, updateTransportDetails.getMainPhoto(), transport);
                } else {
                    transportGalleryService.uploadFiles(
                            files, null, transport);
                }
            });
        }

        if (transport.getStatus().equals(Transport.Status.ACTIVE)) {
            transport.setStatus(Transport.Status.PENDING);
        }

        transportService.save(transport);
    }

    public void updateStatusByTransportIdAndStatus(Transport transport,
                                                   Transport.Status status) {
        transport.setStatus(status);
        if (status.equals(Transport.Status.ACTIVE)) {
            subscriptionPageService.notifyUsers(transport);
        } else {
            subscriptionPageService.removeTransportFromSubscription(transport);
        }
        transportService.save(transport);
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

    private UserDetailsDTO buildUserDetailsDTOFromUser(Users user) {
        UserDetailsDTO dto = new UserDetailsDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setCityId((user.getCity() != null) ? (user.getCity().getId()) : null);
        dto.setPhone(user.getPhone());
        dto.setPhotoUrl(user.getPhotoUrl());
        return dto;
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

}
