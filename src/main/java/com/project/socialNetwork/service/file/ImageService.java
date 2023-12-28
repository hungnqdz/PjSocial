package com.project.socialNetwork.service.file;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface ImageService {
    public String storeFile(MultipartFile file);
    public byte[] readContent(String fileName);
    public void deleteAllFiles();
}
