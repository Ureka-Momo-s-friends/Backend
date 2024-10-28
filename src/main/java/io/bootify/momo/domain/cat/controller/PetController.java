package io.bootify.momo.domain.cat.controller;

import io.bootify.momo.domain.cat.dto.request.PetRequest;
import io.bootify.momo.domain.cat.dto.response.PetResponse;
import io.bootify.momo.domain.cat.service.PetService;
import io.bootify.momo.service.FileStorageService;
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

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/api/pets", produces = MediaType.APPLICATION_JSON_VALUE)
public class PetController {

    private final PetService petService;
    private final FileStorageService fileStorageService;

    @Value("${file.upload-dir}")
    private String fileStorageLocation;

    public PetController(final PetService petService, final FileStorageService fileStorageService) {
        this.petService = petService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping
    public ResponseEntity<List<PetResponse>> getAllPets() {
        return ResponseEntity.ok(petService.findAll());
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<PetResponse>> getPetsByMemberId(@PathVariable Long memberId) {
        List<PetResponse> pets = petService.getPetsByMemberId(memberId);
        return ResponseEntity.ok(pets);
    }

    @PostMapping
    public ResponseEntity<PetResponse> createPet(
            @RequestPart("petData") @Valid final PetRequest petRequest) {
        try {
            // 파일 업로드가 있는지 확인
            MultipartFile profileImage = petRequest.profileImage();

            Long createdId = petService.create(petRequest);

            // 이미지 파일이 있는 경우 처리
            if (profileImage != null && !profileImage.isEmpty()) {
                String filePath = fileStorageService.storePetFile(profileImage, createdId);
                // 프로필 이미지 경로를 설정
                petRequest.setProfileImgUrl(filePath);
                // 업데이트 호출
                petService.update(createdId, petRequest);
            }

            PetResponse createdPet = petService.get(createdId);
            return new ResponseEntity<>(createdPet, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error adding pet: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePet(@PathVariable(name = "id") final Long id,
                                          @RequestPart("petData") @Valid final PetRequest petRequest) {
        try {
            // 파일 업로드가 있는지 확인
            MultipartFile profileImage = petRequest.profileImage();

            if (profileImage != null && !profileImage.isEmpty()) {
                String filePath = fileStorageService.storePetFile(profileImage, id);
                petRequest.setProfileImgUrl(filePath);
            }

            petService.update(id, petRequest);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable(name = "id") final Long id) {
        petService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/uploads/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getFile(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(fileStorageLocation).resolve(fileName).normalize();
            byte[] fileContent = Files.readAllBytes(filePath);
            String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
            MediaType mediaType = fileExtension.equals("png") ? MediaType.IMAGE_PNG : MediaType.IMAGE_JPEG;
            return ResponseEntity.ok().contentType(mediaType).body(fileContent);
        } catch (IOException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
