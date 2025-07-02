package com.hexaware.quitQ_backend_1.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class AwsS3Service {
	
	private final String bucketName = "threshi-quitq";
	
	@Value("${aws.s3.access}")
	private String awsS3AccessKey;

	@Value("${aws.s3.secrete}")
	private String awsS3SecreteKey;
	
	public String saveImageToS3(MultipartFile photo) {
        try {
            String s3FileName = photo.getOriginalFilename();

            // Create AWS credentials
            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsS3AccessKey, awsS3SecreteKey);

            // Create S3 client
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .withRegion(Regions.EU_NORTH_1)
                    .build();

            // Get file input stream and set metadata
            InputStream inputStream = photo.getInputStream();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(photo.getContentType());
            metadata.setContentLength(photo.getSize());

            // Upload to S3
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, s3FileName, inputStream, metadata);
            s3Client.putObject(putObjectRequest);

            // Return public URL
            return "https://" + bucketName + ".s3.eu-north-1.amazonaws.com/" + s3FileName;

        } catch (IOException e) {
            throw new RuntimeException("Unable to upload image to S3 bucket: " + e.getMessage(), e);
        }
    }

}
