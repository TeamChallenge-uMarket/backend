package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.TransportGalleryDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.exception.DataNotValidException;
import com.example.securityumarket.models.Transport;
import com.example.securityumarket.models.TransportGallery;
import com.example.securityumarket.services.storage.CloudinaryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Slf4j
@Getter
@RequiredArgsConstructor
@Service
public class TransportGalleryService {

    private final TransportGalleryDAO transportGalleryDAO;

    private final CloudinaryService cloudinaryService;

    @Value("${cloudinary.default.not-found-photo}")
    private String DEFAULT_PHOTO;

    public void uploadFiles(MultipartFile[] files, String mainPhoto, Transport transport) {
        for (MultipartFile file : files) {
            String fileName = cloudinaryService.uploadFileWithPublicRead(file);
            String fileUrl = cloudinaryService.getOriginalUrl(fileName);
            TransportGallery transportGallery = buildCarGallery(fileName, fileUrl, transport);
            save(transportGallery);

            log.info("Uploaded file '{}' for transport with ID {}.", fileName, transport.getId());
        }
        if (mainPhoto != null) {
            updateMainPhoto(transport, mainPhoto);
        }
    }

    private void deactivateMainPhoto(Transport transport) {
        findMainTransportGalleryByTransportId(transport.getId())
                .ifPresent(transportGallery -> {
                    transportGallery.setMain(false);
                    save(transportGallery);
                });
    }

    private void activateMainPhoto(Transport transport, String mainPhoto) {
        TransportGallery transportGallery = findTransportGalleryByTransportAndImageName(transport, mainPhoto);
        transportGallery.setMain(true);
        save(transportGallery);
    }

    private TransportGallery findTransportGalleryByTransportAndImageName(Transport transport, String imageName) {
        List<TransportGallery> allByTransportId = findAllByTransportId(transport.getId());
        for (TransportGallery gallery : allByTransportId) {
            if (extractTimeExpFromImageName(gallery.getImageName()).equals(imageName)) {
                return gallery;
            }
        }
        throw new DataNotFoundException("Gallery by image name");
    }

    private String extractTimeExpFromImageName(String imageName) {
        int atIndex = imageName.indexOf('_');
        return (atIndex != -1) ? imageName.substring(atIndex + 1) : imageName;
    }

    public TransportGallery findById(Long id) {
        return transportGalleryDAO.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Gallery by id"));
    }

    public void updateMainPhoto(Transport transport, String mainPhoto) {
        deactivateMainPhoto(transport);
        activateMainPhoto(transport, mainPhoto);
    }

    private Optional<TransportGallery> findMainTransportGalleryByTransportId(Long id) {
        return transportGalleryDAO.findMainTransportGalleryByTransportId(id);
    }

    private TransportGallery buildCarGallery(String fileName, String fileUrl, Transport transport) {
        if (!fileName.isEmpty() && fileName.length() < 100)
        {
            if (!fileUrl.isEmpty() && fileUrl.length() < 500) {
                return TransportGallery.builder()
                        .imageName(fileName)
                        .transport(transport)
                        .url(fileUrl)
                        .build();
            } else throw new DataNotValidException("Url must not be empty and longer than 500 characters");
        } else throw new DataNotValidException("The file name must not be empty and longer than 100 characters");
    }

    public String findMainFileByTransport(long carId) {
        return transportGalleryDAO.findMainFileByTransport(carId)
                .orElse(getUrlImageNotFound());
    }

    private String getUrlImageNotFound(){
        return DEFAULT_PHOTO;
    }
    private void save(TransportGallery transportGallery) {
        transportGalleryDAO.save(transportGallery);
        log.info("Saved transport gallery with ID {}.", transportGallery.getId());
    }

    public List<TransportGallery> findAllByTransportId(Long id) {
        return getTransportGalleryDAO().findAllByTransportId(id)
                .orElseThrow(() -> new DataNotFoundException("Gallery"));
    }

    public void deleteTransportGalleryById(Long id) {
        transportGalleryDAO.deleteTransportGalleryById(id);
        log.info("Deleted transport gallery with ID {}.", id);
    }

    public void uploadFiles(MultipartFile[] multipartFiles) {
        for (MultipartFile file : multipartFiles) {
            String fileName = cloudinaryService.uploadFileWithPublicRead(file);
            log.info("Uploaded file '{}'.", fileName);
        }
    }
}
