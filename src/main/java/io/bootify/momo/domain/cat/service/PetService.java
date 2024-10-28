package io.bootify.momo.domain.cat.service;

import io.bootify.momo.domain.cat.dto.request.PetRequest;
import io.bootify.momo.domain.cat.dto.response.PetResponse;
import io.bootify.momo.domain.cat.repository.PetRepository;
import io.bootify.momo.domain.cat.model.Pet;
import io.bootify.momo.util.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(final PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public List<PetResponse> findAll() {
        return petRepository.findAll().stream().map(PetResponse::of).toList();
    }

    public PetResponse get(Long id) {
        Pet pet = petRepository.findById(id).orElseThrow(NotFoundException::new);
        return PetResponse.of(pet);
    }

    public List<PetResponse> getPetsByMemberId(Long memberId) {
        return petRepository.findByMemberId(memberId).stream().map(PetResponse::of).toList();
    }

    public Long create(PetRequest petRequest) {
        Pet pet = mapToEntity(petRequest);
        return petRepository.save(pet).getId();
    }

    public void update(Long id, PetRequest petRequest) {
        Pet pet = petRepository.findById(id).orElseThrow(NotFoundException::new);
        mapToEntity(petRequest, pet);
        petRepository.save(pet);
    }

    public void delete(Long id) {
        petRepository.deleteById(id);
    }

    private Pet mapToEntity(PetRequest petRequest) {
        Pet pet = new Pet();
        pet.setPetName(petRequest.petName());
        pet.setBirthDate(petRequest.birthDate());
        pet.setBreed(petRequest.breed());
        pet.setGender(petRequest.gender());
        return pet;
    }

    private void mapToEntity(PetRequest petRequest, Pet pet) {
        pet.setPetName(petRequest.petName());
        pet.setBirthDate(petRequest.birthDate());
        pet.setBreed(petRequest.breed());
        pet.setGender(petRequest.gender());

        if (petRequest.profileImage() != null && !petRequest.profileImage().isEmpty()) {
            // 파일을 처리하고, 해당 경로를 설정
            pet.setProfileImgUrl("/uploads/" + petRequest.profileImage().getOriginalFilename());
        } else {
            pet.setProfileImgUrl(petRequest.profileImgUrl()); // 기존 URL 설정
        }
    }
}

