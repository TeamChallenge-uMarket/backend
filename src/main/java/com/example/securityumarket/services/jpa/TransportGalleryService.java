package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.TransportGalleryDAO;
import com.example.securityumarket.exception.DataNotValidException;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.entities.Transport;
import com.example.securityumarket.models.entities.TransportGallery;
import com.example.securityumarket.services.page_service.StorageService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Service
public class TransportGalleryService {

    private final TransportGalleryDAO transportGalleryDAO;

    private final StorageService storageService;

    public void uploadFiles(MultipartFile[] files, Transport transport) {
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            String fileName = storageService.uploadFileWithPublicRead(file).substring(16);
            String fileUrl = storageService.getFileUrlFromPublicRead(fileName);
            boolean isMain = (i == 0);
            TransportGallery transportGallery = buildCarGallery(fileName, fileUrl, transport, isMain);
            save(transportGallery);
        }
    }

    private TransportGallery buildCarGallery(String fileName, String fileUrl, Transport transport, boolean isMain) {
        if (!fileName.isEmpty() && fileName.length() < 100)
        {
            if (!fileUrl.isEmpty() && fileUrl.length() < 500) {
                return TransportGallery.builder()
                        .imageName(fileName)
                        .transport(transport)
                        .url(fileUrl)
                        .isMain(isMain)
                        .build();
            } else throw new DataNotValidException("The file name must not be empty and longer than 100 characters");
        } else throw new DataNotValidException("The file name must not be empty and longer than 100 characters");
    }

    public List<TransportGallery> findCarGalleriesByImageNames(List<String> imageNames) {
        return transportGalleryDAO.findCarGalleriesByImageNames(imageNames)
                .filter(fileList -> !fileList.isEmpty())
                .orElseThrow(() -> new DataNotFoundException("Gallery"));
    }

    public String findMainFileByTransport(long carId) {
        return transportGalleryDAO.findMainFileByTransport(carId)
                .orElse("The main photo of the transport was not found");//TODO Add file with this name
    }

    private void save(TransportGallery transportGallery) {
        transportGalleryDAO.save(transportGallery);
    }
}