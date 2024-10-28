package io.bootify.momo.domain.cat.controller;

import io.bootify.momo.domain.cat.dto.request.PetRequest;
import io.bootify.momo.domain.cat.dto.response.PetResponse;
import io.bootify.momo.domain.cat.model.Pet;
import io.bootify.momo.domain.cat.service.PetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/api/pets", produces = "application/json")
public class PetController {

    private final PetService petService;

    public PetController(final PetService petService) {
        this.petService = petService;
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
            @RequestPart("petData") @Valid final PetRequest petRequest,
            @RequestPart(value = "profileImg", required = false) MultipartFile profileImg) throws IOException {
        // 프로필 이미지가 있는지 확인
        if (profileImg != null) {
            System.out.println("Profile image received: " + profileImg.getOriginalFilename());
        } else {
            System.out.println("No profile image found");
        }

        // 프로필 이미지와 함께 새로운 PetRequest 생성
        PetRequest updatedRequest = new PetRequest(
                petRequest.petName(),
                petRequest.breed(),
                petRequest.birthDate(),
                petRequest.gender(),
                petRequest.memberId(),
                profileImg);

        // 서비스에서 Pet 생성 및 저장
        Long id = petService.create(updatedRequest);

        // 생성된 Pet 엔티티 가져오기
        Pet createdPet = petService.getEntity(id);

        // PetResponse 객체 생성 후 반환
        return ResponseEntity.ok(PetResponse.of(createdPet));
    }



    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePet(@PathVariable(name = "id") final Long id,
                                          @RequestPart("petData") @Valid final PetRequest petRequest) {
        try {
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
}
