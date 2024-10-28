package io.bootify.momo.domain.cat.controller;

import io.bootify.momo.domain.cat.dto.request.PetRequest;
import io.bootify.momo.domain.cat.dto.response.PetResponse;
import io.bootify.momo.domain.cat.service.PetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
            @RequestPart("petData") @Valid final PetRequest petRequest) {
        try {
            Long createdId = petService.create(petRequest);
            PetResponse createdPet = petService.get(createdId);
            return new ResponseEntity<>(createdPet, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
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
