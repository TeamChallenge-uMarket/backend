package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.TransportGalleryDAO;
import com.example.securityumarket.exception.DataNotValidException;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.entities.Transport;
import com.example.securityumarket.models.entities.TransportGallery;
import com.example.securityumarket.services.storage.CloudinaryService;
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

    private final CloudinaryService cloudinaryService;

//    private final StorageService storageService;

    public void uploadFiles(MultipartFile[] files, Transport transport) {
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            String fileName = cloudinaryService.uploadFileWithPublicRead(file);
            String fileUrl = cloudinaryService.getOriginalUrl(fileName);
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
            } else throw new DataNotValidException("Url must not be empty and longer than 500 characters");
        } else throw new DataNotValidException("The file name must not be empty and longer than 100 characters");
    }

    public List<TransportGallery> findCarGalleriesByImageNames(List<String> imageNames) {
        return transportGalleryDAO.findCarGalleriesByImageNames(imageNames)
                .filter(fileList -> !fileList.isEmpty())
                .orElseThrow(() -> new DataNotFoundException("Gallery"));
    }

    public String findMainFileByTransport(long carId) {
        return transportGalleryDAO.findMainFileByTransport(carId)
                .orElse(getUrlImageNotFound());
    }

    private String getUrlImageNotFound(){
        return "https://res.cloudinary.com/de4bysqtm/image/upload/v1697906978/czkhxykmkfn92deqncp5.jpg";
    }
    private void save(TransportGallery transportGallery) {
        transportGalleryDAO.save(transportGallery);
    }

    public List<TransportGallery> findAllByTransportId(Long id) {
        return getTransportGalleryDAO().findAllByTransportId(id)
                .orElseThrow(() -> new DataNotFoundException("Gallery"));
    }

    public List<String> findAllUrlByTransportId(Long transportId) {
        List<TransportGallery> allByTransportId = findAllByTransportId(transportId);
        return allByTransportId.stream().map(TransportGallery::getUrl).toList();
    }
}
