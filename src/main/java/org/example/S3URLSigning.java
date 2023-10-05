package org.example;

import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;

public class S3URLSigning {

    public static String getS3ObjectSignedURL(String bucket_name, String object_key, String region) {
        return getS3ObjectSignedURL(bucket_name, object_key, region, 600);
    }
    public static String getS3ObjectSignedURL(String bucket_name, String object_key, String region, int expiration_in_seconds) {
        String url = "";
        EnvironmentVariableCredentialsProvider credentialsProvider = EnvironmentVariableCredentialsProvider.create();

        S3Presigner presigner = S3Presigner.builder().
                region(Region.of(region)).
                credentialsProvider(credentialsProvider).
                build();
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucket_name).key(object_key).build();

            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofSeconds(expiration_in_seconds))
                    .getObjectRequest(getObjectRequest)
                    .build();

            PresignedGetObjectRequest presignedGetObjectRequest = presigner.presignGetObject(presignRequest);
            url = presignedGetObjectRequest.url().toString();
            ;
        } catch (Exception e) {
            url = e.toString();
        } finally {
            presigner.close();
        }
        return url;
    }

    public static String PutS3ObjectSignedURL(String bucket_name, String object_key, String region) {
        return PutS3ObjectSignedURL(bucket_name, object_key, region, 600);
    }

    public static String PutS3ObjectSignedURL(String bucket_name, String object_key, String region, int expiry_in_secs) {
        String url = "";
        EnvironmentVariableCredentialsProvider credentialsProvider = EnvironmentVariableCredentialsProvider.create();

        S3Presigner presigner = S3Presigner.builder().
                region(Region.of(region)).
                credentialsProvider(credentialsProvider).
                build();

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket_name)
                    .key(object_key)
                    .build();

            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofSeconds(expiry_in_secs))
                    .putObjectRequest(putObjectRequest).build();

            PresignedPutObjectRequest presignedPutObjectRequest = presigner.presignPutObject(presignRequest);
            url = presignedPutObjectRequest.url().toString();
        } catch (Exception e) {
            url = e.toString();
        } finally {
            presigner.close();
        }

        return url;
    }
}