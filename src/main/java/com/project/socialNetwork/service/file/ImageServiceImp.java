package com.project.socialNetwork.service.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@Slf4j
public class ImageServiceImp implements ImageService {
    private final Path storageFolder = Paths.get("image");

    private boolean isImage(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[]{"jpg", "png", "jpeg", "bmp"}).contains(fileExtension.trim().toLowerCase());
    }

    public ImageServiceImp() {
        try {
            Files.createDirectories(storageFolder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String storeFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Empty");
            }
            if (!isImage(file)) {
                throw new RuntimeException("You can only upload image file");
            }
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String fileName = UUID.randomUUID().toString().replace("-", "");
            fileName = fileName + "." + fileExtension;
            Path destinationFilePath = Paths.get(storageFolder.toAbsolutePath() + "/" + fileName);
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
            return fileName;
        } catch (Exception e) {
            throw new RuntimeException("Failed to store image", e);
        }
    }

    @Override
    public byte[] readContent(String fileName) {
        try {
            Path imagePath = storageFolder.resolve(fileName);
            Resource resource = new UrlResource(imagePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return bytes;
            } else {
                throw new RuntimeException("Cannot read " + fileName);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAllFiles() {

    }
}
