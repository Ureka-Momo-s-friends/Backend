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

    @Value("${file.upload-dir:uploads}")
    private String fileStorageLocation;

    // 사용자 프로필 이미지 저장
    public String storeUserFile(MultipartFile file, Long id) {
        String fileName = "user_" + id + "_" + file.getOriginalFilename();
        return storeFile(file, fileName, "user");
    }

    // 고양이 프로필 이미지 저장
    public String storePetFile(MultipartFile file, Long id) {
        String fileName = "pet_" + id + "_" + file.getOriginalFilename();
        return storeFile(file, fileName, "pet");
    }

    private String storeFile(MultipartFile file, String fileName, String folder) {
        try {
            Path targetLocation = Paths.get(fileStorageLocation).resolve(folder).resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return "/uploads/" + folder + "/" + fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Failed to store file.", ex);
        }
    }
}

