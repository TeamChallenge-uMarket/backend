package com.example.securityumarket.services.storage;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.securityumarket.exception.CloudinaryException;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.exception.DataNotValidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    @Value("${file.upload.minSize}")
    private long minFileSize;

    @Value("${file.upload.maxSize}")
    private long maxFileSize;


    public String uploadFileWithPublicRead(MultipartFile file) {
        if (file.getSize() >= minFileSize && file.getSize() <= maxFileSize) {
            String contentType = file.getContentType();
            if (Objects.requireNonNull(contentType).startsWith("image/")
                    || contentType.startsWith("video/")) {
                File fileObj = convertMultiPartFileToFile(file);
                String fileName = System.currentTimeMillis() +
                        "_" + file.getOriginalFilename();
                try {
                    Map uploadResult = cloudinary.uploader()
                            .upload(fileObj, Map.of("public_id", fileName));
                    log.info("File '{}' uploaded successfully.", fileName);
                    return uploadResult.get("public_id").toString();
                } catch (IOException e) {
                    log.error("Failed to upload file '{}': {}", fileName, e.getMessage(), e);
                    throw new CloudinaryException();
                } finally {
                    fileObj.delete();
                }
            } else {
                throw new DataNotValidException("Unsupported file type. Please upload photos or videos.");
            }
        } else {
            throw new DataNotValidException("File size must be between " +
                    minFileSize + " and " + maxFileSize + " bytes.");
        }
    }

    public File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            throw new DataNotValidException("multipartFile is not valid");
        }
        return convertedFile;
    }

    public String getOriginalUrl(String fileName) {
        try {
            Map resourceInfo = cloudinary.api().resource(fileName, ObjectUtils.emptyMap());
            return (String) resourceInfo.get("url");
        } catch (Exception e) {
            throw new DataNotFoundException("File with the name " + fileName);
        }
    }


    public void deleteFile(String publicId) {
        try {
            Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            if (result.get("result").equals("ok")) {
                String text = String.format("File with publicId %s has been successfully deleted.",
                        publicId);
                log.info(text);
                ResponseEntity.ok(text);
            } else {
                String text = String.format("Failed to delete file with %s", publicId);
                log.error(text);
                throw new DataNotFoundException(publicId);
            }
        } catch (Exception e) {
            String text = String.format("Error deleting file with publicId %s %s", publicId, e);
            throw new CloudinaryException(text);
        }
    }

    public static String getPhotoPublicIdFromUrl(String photoUrl) {
        photoUrl = photoUrl.substring(photoUrl.lastIndexOf('/'));
        String publicId = photoUrl.substring(photoUrl.lastIndexOf('/') + 1);
        return publicId.substring(0, publicId.lastIndexOf('.'));
    }
}