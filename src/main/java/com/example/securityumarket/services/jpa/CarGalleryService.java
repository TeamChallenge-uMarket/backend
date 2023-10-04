package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.CarGalleryDAO;
import com.example.securityumarket.exception.DataNotValidException;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.entities.Car;
import com.example.securityumarket.models.entities.CarGallery;
import com.example.securityumarket.services.page_service.StorageService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Service
public class CarGalleryService {

    private final CarGalleryDAO carGalleryDAO;

    private final StorageService storageService;

    public void uploadFiles(MultipartFile[] files, Car car) {
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            String fileName = storageService.uploadFileWithPublicRead(file).substring(16);
            String fileUrl = storageService.getFileUrlFromPublicRead(fileName);
            boolean isMain = (i == 0);
            CarGallery carGallery = buildCarGallery(fileName, fileUrl, car, isMain);
            save(carGallery);
        }
    }

    private CarGallery buildCarGallery(String fileName, String fileUrl, Car car, boolean isMain) {
        if (!fileName.isEmpty() && fileName.length() < 100)
        {
            if (!fileUrl.isEmpty() && fileUrl.length() < 500) {
                return CarGallery.builder()
                        .imageName(fileName)
                        .car(car)
                        .url(fileUrl)
                        .isMain(isMain)
                        .build();
            } else throw new DataNotValidException("The file name must not be empty and longer than 100 characters");
        } else throw new DataNotValidException("The file name must not be empty and longer than 100 characters");
    }

    public List<CarGallery> findCarGalleriesByImageNames(List<String> imageNames) {
        return carGalleryDAO.findCarGalleriesByImageNames(imageNames)
                .filter(fileList -> !fileList.isEmpty())
                .orElseThrow(() -> new DataNotFoundException("Gallery"));
    }

    public String findMainFileByCar(long carId) {
        return carGalleryDAO.findMainFileByCar(carId)
                .orElse("The main photo of the car was not found");//TODO Add file with this name
    }

    private void save(CarGallery carGallery) {
        carGalleryDAO.save(carGallery);
    }
}
