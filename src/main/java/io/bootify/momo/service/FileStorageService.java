package io.bootify.momo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String fileStorageLocation;

    public String storeFile(MultipartFile file, Long id) {
        String fileName = id + "_" + file.getOriginalFilename();

        try {
            Path targetLocation = Paths.get(fileStorageLocation).resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/" + fileName; // 반환되는 경로가 클라이언트가 접근 가능한 경로여야 함
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to store file.", ex);
        }
    }
}
