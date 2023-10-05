package org.example;

public class Main {
    public static void main(String args[]) {
        String signedURL = S3URLSigning.getS3ObjectSignedURL("bucker_name", "object_1.txt", "region");
        System.out.println(signedURL);
    }
}
