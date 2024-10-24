package io.bootify.momo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    // 파일이 저장될 기본 경로를 application.properties에서 가져옴
    @Value("${file.upload-dir}")
    private String fileStorageLocation;

    public String storeFile(MultipartFile file, Long id) {
        // 파일 이름 생성
        String fileName = id + "_" + file.getOriginalFilename();

        try {
            // 저장 경로 설정 및 파일 저장
            Path targetLocation = Paths.get(fileStorageLocation).resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // 경로 출력
            System.out.println("Stored file path: " + targetLocation.toString());

            return "/uploads/" + fileName;
        } catch (IOException ex) {
            ex.printStackTrace(); // 에러 출력
            throw new RuntimeException("파일을 저장하는 데 실패했습니다.", ex);
        }
    }

}
