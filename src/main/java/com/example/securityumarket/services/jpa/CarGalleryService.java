package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.CarGalleryDAO;
import com.example.securityumarket.exception.UAutoException;
import com.example.securityumarket.models.entities.CarGallery;
import com.example.securityumarket.services.page_service.StorageService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Service
public class CarGalleryService {
    @Value("${file.upload.maxSize}")
    private long minFileSize;

    @Value("${file.upload.minSize}")
    private long maxFileSize;

    private final CarGalleryDAO carGalleryDAO;

    private final StorageService storageService;

    private List<String> uploadFiles(MultipartFile[] files) throws UAutoException {
        List<String> uploadedFileUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = storageService.uploadFileWithPublicRead(file).substring(16);
            String fileUrl = storageService.getFileUrlFromPublicRead(fileName);
            CarGallery carGallery = buildCarGallery(fileName, fileUrl);
            save(carGallery);
            uploadedFileUrls.add(fileName);
        }
        return uploadedFileUrls;
    }

    private void save(CarGallery carGallery) {
        carGalleryDAO.save(carGallery);
    }

    private CarGallery buildCarGallery(String fileName, String fileUrl) throws UAutoException {
        if (!fileName.isEmpty() && fileName.length() < 100)
        {
            if (!fileUrl.isEmpty() && fileUrl.length() < 500) {
                return CarGallery.builder()
                        .imageName(fileName)
                        .url(fileUrl)
                        .isMain(false)
                        .build();
            } else throw new UAutoException("The file name must not be empty and longer than 100 characters");
        } else throw new UAutoException("The file name must not be empty and longer than 100 characters");
    }



    public List<CarGallery> findCarGalleriesByImageNames(MultipartFile[] files) {
        List<String> uploadFiles = uploadFiles(files);
        return carGalleryDAO.findCarGalleriesByImageNames(uploadFiles)
                .filter(fileList -> !fileList.isEmpty())
                .orElseThrow(() -> new UAutoException("Gallery not found"));
    }
}
