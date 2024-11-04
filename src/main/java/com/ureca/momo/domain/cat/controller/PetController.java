package com.ureca.momo.domain.cat.controller;

import com.ureca.momo.domain.cat.dto.request.PetRequest;
import com.ureca.momo.domain.cat.dto.response.PetResponse;
import com.ureca.momo.domain.cat.model.Pet;
import com.ureca.momo.domain.cat.service.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/api/pets", produces = MediaType.APPLICATION_JSON_VALUE)
public class PetController {

    private final PetService petService;

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<PetResponse>> getPetsByMemberId(@PathVariable("memberId") Long memberId) {
        List<PetResponse> pets = petService.getPetsByMemberId(memberId);
        return ResponseEntity.ok(pets);
    }

    @PostMapping
    public ResponseEntity<PetResponse> createPet(
            @RequestPart("petData") @Valid final PetRequest petRequest,
            @RequestPart(value = "profileImg", required = false) MultipartFile profileImg) throws IOException {

        // Check if profile image is received
        if (profileImg != null) {
            System.out.println("Profile image received: " + profileImg.getOriginalFilename());
        } else {
            System.out.println("No profile image found");
        }

        // Create a new PetRequest with the received data
        PetRequest updatedRequest = new PetRequest(
                petRequest.petName(),
                petRequest.breed(),
                petRequest.birthDate(),
                petRequest.gender(),
                petRequest.memberId(),
                profileImg);

        // Create and save the new pet
        Long id = petService.create(updatedRequest);

        // Retrieve the created Pet entity
        Pet createdPet = petService.getEntity(id);

        // Return the PetResponse object
        return ResponseEntity.status(HttpStatus.CREATED).body(PetResponse.of(createdPet));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePet(@PathVariable(name = "id") final Long id,
                                          @RequestPart("petData") @Valid final PetRequest petRequest,
                                          @RequestPart(value = "profileImg", required = false) MultipartFile profileImg) {
        try {
            // Update the pet using the service
            petService.update(id, petRequest, profileImg);
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
