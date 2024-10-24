package io.bootify.momo.rest;

import io.bootify.momo.model.PetDTO;
import io.bootify.momo.service.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000") // React 개발 서버 주소
@RestController
@RequestMapping(value = "/api/pets", produces = MediaType.APPLICATION_JSON_VALUE)
public class PetResource {

    private final PetService petService;

    @Value("${file.upload-dir}")
    private String fileStorageLocation;

    public PetResource(final PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public ResponseEntity<List<PetDTO>> getAllPets() {
        return ResponseEntity.ok(petService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDTO> getPet(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(petService.get(id));
    }

    @PostMapping
    public ResponseEntity<PetDTO> createPet(
            @RequestPart("petData") @Valid final PetDTO petDTO,
            @RequestPart(value = "profileImgUrl", required = false) MultipartFile profileImgUrl)
    {
        // 먼저 고양이를 데이터베이스에 저장하여 ID를 생성합니다.
        Long createdId = petService.create(petDTO);

        // ID가 생성된 이후에 파일을 저장합니다.
        if (profileImgUrl != null && !profileImgUrl.isEmpty()) {
            String filePath = storeFile(profileImgUrl, createdId); // 생성된 ID 사용
            petDTO.setProfileImgUrl(filePath);
            petService.update(createdId, petDTO); // 파일 경로 업데이트
        }

        // 생성된 ID를 통해 새로 저장된 고양이 데이터를 가져옵니다.
        PetDTO createdPet = petService.get(createdId);

        // 새로 생성된 고양이 데이터를 반환합니다.
        return new ResponseEntity<>(createdPet, HttpStatus.CREATED);
    }



    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePet(@PathVariable(name = "id") final Long id,
                                          @RequestPart("petData") @Valid final PetDTO petDTO,
                                          @RequestPart(value = "profileImgUrl", required = false) MultipartFile profileImgUrl) {
        if (profileImgUrl != null && !profileImgUrl.isEmpty()) {
            String filePath = storeFile(profileImgUrl, petDTO.getId());
            petDTO.setProfileImgUrl(filePath);

            // 파일 경로가 제대로 설정됐는지 확인
            System.out.println("File path set to PetDTO: " + petDTO.getProfileImgUrl());
        }

        petService.update(id, petDTO);
        return ResponseEntity.ok(id);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable(name = "id") final Long id) {
        petService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 이미지 제공 엔드포인트 추가
    @GetMapping(value = "/uploads/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getFile(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(fileStorageLocation).resolve(fileName).normalize();
            byte[] fileContent = Files.readAllBytes(filePath);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(fileContent);
        } catch (IOException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    private String storeFile(MultipartFile file, Long petId) {
        // Implement file storage logic here (similar to what was provided in FileStorageService)
        // Use petId and other info to generate a unique filename, and return the stored file's path
        return "";
    }
}
