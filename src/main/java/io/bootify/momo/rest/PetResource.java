package io.bootify.momo.rest;

import io.bootify.momo.model.PetDTO;
import io.bootify.momo.service.FileStorageService;
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

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/api/pets", produces = MediaType.APPLICATION_JSON_VALUE)
public class PetResource {

    private final PetService petService;
    private final FileStorageService fileStorageService;

    @Value("${file.upload-dir}")
    private String fileStorageLocation;

    public PetResource(final PetService petService, final FileStorageService fileStorageService) {
        this.petService = petService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping
    public ResponseEntity<List<PetDTO>> getAllPets() {
        return ResponseEntity.ok(petService.findAll());
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<PetDTO>> getPetsByMemberId(@PathVariable Long memberId) {
        List<PetDTO> pets = petService.getPetsByMemberId(memberId);
        return ResponseEntity.ok(pets);
    }

    @PostMapping
    public ResponseEntity<PetDTO> createPet(
            @RequestPart("petData") @Valid final PetDTO petDTO,
            @RequestPart(value = "profileImgUrl", required = false) MultipartFile profileImgUrl) {
        try {
            Long createdId = petService.create(petDTO);

            if (profileImgUrl != null && !profileImgUrl.isEmpty()) {
                String filePath = fileStorageService.storeFile(profileImgUrl, createdId);
                petDTO.setProfileImgUrl(filePath);
                petService.update(createdId, petDTO);
            }

            PetDTO createdPet = petService.get(createdId);
            return new ResponseEntity<>(createdPet, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            // 오류 로그 추가
            System.err.println("Error adding pet: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePet(@PathVariable(name = "id") final Long id,
                                          @RequestPart("petData") @Valid final PetDTO petDTO,
                                          @RequestPart(value = "profileImgUrl", required = false) MultipartFile profileImgUrl) {
        try {
            if (profileImgUrl != null && !profileImgUrl.isEmpty()) {
                // 파일 저장 로직 수정
                String filePath = fileStorageService.storeFile(profileImgUrl, id);
                petDTO.setProfileImgUrl(filePath);
            }

            petService.update(id, petDTO);
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
            // 확장자에 따라 Content-Type을 설정 (예시: PNG, JPEG)
            String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
            MediaType mediaType = fileExtension.equals("png") ? MediaType.IMAGE_PNG : MediaType.IMAGE_JPEG;
            return ResponseEntity.ok().contentType(mediaType).body(fileContent);
        } catch (IOException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
