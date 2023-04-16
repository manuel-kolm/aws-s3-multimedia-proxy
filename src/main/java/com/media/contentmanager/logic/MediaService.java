package com.media.contentmanager.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;

@Service
public class MediaService {

    public final S3Client s3Client;

    @Autowired
    public MediaService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public ResponseEntity<byte[]> getImage(String keyName, String bucketName) throws IOException {
        GetObjectRequest objectRequest = GetObjectRequest.builder() //
                .key(keyName) //
                .bucket(bucketName) //
                .build();

        var responseInputStream = s3Client.getObject(objectRequest);
        var objectResponse = responseInputStream.response();

        return ResponseEntity.ok() //
                .contentType(MediaType.parseMediaType(objectResponse.contentType()))
                .body(responseInputStream.readAllBytes());
    }

    public ResponseEntity<byte[]> getResizedImage(String keyName, String bucketName, int width, int height) throws IOException {
        ResponseEntity<byte[]> originalResponse = getImage(keyName, bucketName);
        byte[] originalImage = originalResponse.getBody();

        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawBytes(originalImage, 0, 0, width, height);
        graphics2D.dispose();

        DataBufferByte buffer = (DataBufferByte) resizedImage.getRaster().getDataBuffer();
        return ResponseEntity.ok() //
                .contentType(originalResponse.getHeaders().getContentType())
                .body(buffer.getData());
    }
}
