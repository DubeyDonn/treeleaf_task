package com.test.treeleaf.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.test.treeleaf.model.ThumbnailImage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageService() {
        this.fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        String fileName = originalFileName;
        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            // Check if file already exists and rename if necessary
            while (Files.exists(targetLocation)) {
                String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                fileName = timestamp + "_" + originalFileName;
                targetLocation = this.fileStorageLocation.resolve(fileName);
            }
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    // delete file by name
    public void deleteFile(String fileName) {
        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.delete(targetLocation);
        } catch (IOException ex) {
            throw new RuntimeException("Could not delete file " + fileName + ". Please try again!", ex);
        }
    }

    // delete files by names
    public void deleteFiles(List<ThumbnailImage> thumbnailImages) {
        for (ThumbnailImage thumbnailImage : thumbnailImages) {
            deleteFile(thumbnailImage.getName());
        }
    }
}
