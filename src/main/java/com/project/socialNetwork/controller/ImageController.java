package com.project.socialNetwork.controller;

import com.project.socialNetwork.service.file.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<byte[]> serveImage(@PathVariable String fileName) {
        try {
            byte[] bytes = imageService.readContent(fileName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }
}

