package com.example.securityumarket.services.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.exception.DataNotValidException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

@Slf4j
@Getter
@Service
public class StorageService {

    @Value("${application.bucket.name}")
    private String bucketName;

    @Value("${file.upload.minSize}")
    private long minFileSize;

    @Value("${file.upload.maxSize}")
    private long maxFileSize;

    private final AmazonS3 s3Client;

    public StorageService(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public byte[] downloadFile(String fileName) {
        S3Object s3Object = s3Client.getObject(getBucketName(), fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String deleteFile(String fileName) {
        s3Client.deleteObject(getBucketName(), fileName);
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
        if (file.getSize() >= minFileSize && file.getSize() <= maxFileSize) {
            String contentType = file.getContentType();
            if (Objects.requireNonNull(contentType).startsWith("image/") || contentType.startsWith("video/")) {
                File fileObj = convertMultiPartFileToFile(file);
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                try {
                    s3Client.putObject(new PutObjectRequest(getBucketName(), fileName, fileObj)
                            .withCannedAcl(CannedAccessControlList.PublicRead));

                } catch (AmazonS3Exception ignored) {
                } finally {
                    fileObj.delete();
                }
                return "File uploaded : " + fileName;
            } else {
                throw new DataNotValidException("Unsupported file type. Please upload photos or videos.");
            }
        } else {
            throw new DataNotValidException("File size must be between " + minFileSize + " and " + maxFileSize + " bytes.");
        }
    }

    public String getFileUrlFromPublicRead(String fileName) {
        if (s3Client.doesObjectExist(getBucketName(), fileName)) {
            GeneratePresignedUrlRequest urlRequest =
                    new GeneratePresignedUrlRequest(getBucketName(), fileName);
            // getFileUrlFromPrivateRead - urlRequest.setExpiration(Date.from(Instant.now().plus(Duration.ofHours(1))));
            urlRequest.addRequestParameter("Content-Disposition", "inline");
            URL url = s3Client.generatePresignedUrl(urlRequest);
            return url.toString();
        } else {
            throw new DataNotFoundException("File with the name " + fileName);
        }
    }
}