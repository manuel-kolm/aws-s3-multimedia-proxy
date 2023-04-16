package com.multimedia.proxy.controller;

import com.multimedia.proxy.logic.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class MediaController {

    private final MediaService mediaService;

    @Autowired
    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @GetMapping("/image/{keyName}/{bucketName}")
    public ResponseEntity<byte[]> getImage(@RequestPart String keyName, @RequestPart String bucketName) throws IOException {
        return mediaService.getImage(keyName, bucketName);
    }

    @GetMapping("/resized-image/{keyName}/{bucketName}")
    public ResponseEntity<byte[]> getResizedImage(@RequestPart String keyName, @RequestPart String bucketName,
                                                  @RequestParam int width, @RequestParam int height)
            throws IOException {
        return mediaService.getResizedImage(keyName, bucketName, width, height);
    }
}
