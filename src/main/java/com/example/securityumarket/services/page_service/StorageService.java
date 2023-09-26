package com.example.securityumarket.services.page_service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Service
public class StorageService {

    @Value("${application.bucker.name}")
    private String bucketName;

    private final AmazonS3 s3Client;

    public StorageService(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public byte[] downloadFile(String fileName) {
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] byteArray = IOUtils.toByteArray(inputStream);
            return byteArray;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String deleteFile(String fileName) {
        s3Client.deleteObject(bucketName,fileName);
        return fileName + " removed.";
    }

    public File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }

    public String uploadFileWithPublicRead(MultipartFile file) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName,fileName,fileObj)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        fileObj.delete();
        return "File uploaded : " + fileName;
    }

    public String getPhotoUrlFromPublicRead(String fileName)
    {
        GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(bucketName, fileName);
        ResponseHeaderOverrides headers = new ResponseHeaderOverrides();
        urlRequest.addRequestParameter("Content-Disposition", "inline");
        URL url = s3Client.generatePresignedUrl(urlRequest);
        return url.toString();
    }

    public String uploadFileWithPrivateRead(MultipartFile file) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName,fileName,fileObj));
        fileObj.delete();
        return "File uploaded : " + fileName;
    }

    public String getPhotoUrlFromPrivateRead(String fileName)
    {
        GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(bucketName, fileName);
        urlRequest.setExpiration(Date.from(Instant.now().plus(Duration.ofHours(1))));
        return s3Client.generatePresignedUrl(urlRequest).toString();
    }



}
