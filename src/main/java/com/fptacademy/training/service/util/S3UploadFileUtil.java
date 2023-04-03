package com.fptacademy.training.service.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.fptacademy.training.exception.ResourceBadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class S3UploadFileUtil {

    private final AmazonS3 s3Client;

    @Value("${amazon.s3.bucketName}")
    private String bucketName;

    @Value("${resource.file.upload}")
    private String rootFile;

    public Object handleFileUpload(MultipartFile file, String type) {
        try {
            // Generate a unique filename for the uploaded file
            String filename = rootFile + file.getOriginalFilename();

            // Upload the file to S3
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            metadata.addUserMetadata(type, filename);
            PutObjectRequest putRequest = new PutObjectRequest(bucketName, filename, file.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);
            ;
            PutObjectResult result = s3Client.putObject(putRequest);
            String url = s3Client.getUrl(bucketName, filename).toString();
            // Return the URL of the uploaded file
            return url;
        } catch (IOException e) {
            throw new ResourceBadRequestException("S3UploadFileUtil-HandleFileUpload-FAIL");
        }
    }
}
