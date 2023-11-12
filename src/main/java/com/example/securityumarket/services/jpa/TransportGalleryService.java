package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.TransportGalleryDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.exception.DataNotValidException;
import com.example.securityumarket.models.entities.Transport;
import com.example.securityumarket.models.entities.TransportGallery;
import com.example.securityumarket.services.storage.CloudinaryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Service
public class TransportGalleryService {

    private final TransportGalleryDAO transportGalleryDAO;

    private final CloudinaryService cloudinaryService;

    @Value("${cloudinary.default.not-found-photo}")
    private String DEFAULT_PHOTO;

//    private final StorageService storageService;

    public void uploadFiles(MultipartFile[] files, String mainPhoto, Transport transport) {
        for (MultipartFile file : files) {
            String fileName = cloudinaryService.uploadFileWithPublicRead(file);
            String fileUrl = cloudinaryService.getOriginalUrl(fileName);
            TransportGallery transportGallery = buildCarGallery(fileName, fileUrl, transport);
            save(transportGallery);
        }
        if (mainPhoto != null) {
            updateMainPhoto(transport, mainPhoto);
        }
    }

    private void deactivateMainPhoto(Transport transport) {
        TransportGallery mainTransportGallery = findMainTransportGalleryByTransportId(transport.getId());
        mainTransportGallery.setMain(false);
        save(mainTransportGallery);
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

    private TransportGallery findMainTransportGalleryByTransportId(Long id) {
        return transportGalleryDAO.findMainTransportGalleryByTransportId(id)
                .orElseThrow(() -> new DataNotFoundException("Gallery"));
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
    }

    public List<TransportGallery> findAllByTransportId(Long id) {
        return getTransportGalleryDAO().findAllByTransportId(id)
                .orElseThrow(() -> new DataNotFoundException("Gallery"));
    }

    @Transactional
    public ResponseEntity<String> deleteGalleryTransportByGalleryId(List<Long> galleriesId) {
        for (Long galleryId : galleriesId) {
            TransportGallery gallery = findById(galleryId);
            cloudinaryService.deleteFile(gallery.getImageName());
            transportGalleryDAO.deleteTransportGalleryById(gallery.getId());
        }
        return ResponseEntity.ok("The files have been successfully deleted");
    }
}
