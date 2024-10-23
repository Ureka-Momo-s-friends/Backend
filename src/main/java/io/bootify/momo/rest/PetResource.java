package io.bootify.momo.rest;

import io.bootify.momo.model.PetDTO;
import io.bootify.momo.service.PetService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000") // React 개발 서버 주소
@RestController
@RequestMapping(value = "/api/pets", produces = MediaType.APPLICATION_JSON_VALUE)
public class PetResource {

    private final PetService petService;

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
    public ResponseEntity<Long> createPet(@RequestBody @Valid final PetDTO petDTO) {
        final Long createdId = petService.create(petDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePet(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final PetDTO petDTO) {
        petService.update(id, petDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable(name = "id") final Long id) {
        petService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
