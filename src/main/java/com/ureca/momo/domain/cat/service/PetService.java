package com.ureca.momo.domain.cat.service;

import com.ureca.momo.domain.cat.dto.request.PetRequest;
import com.ureca.momo.domain.cat.dto.response.PetResponse;
import com.ureca.momo.domain.cat.model.Pet;
import com.ureca.momo.domain.cat.repository.PetRepository;
import com.ureca.momo.domain.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final MemberRepository memberRepository;

    public List<PetResponse> getPetsByMemberId(Long memberId) {
        List<Pet> pets = petRepository.findByMemberId(memberId);
        return pets.stream()
                .map(PetResponse::of)
                .collect(Collectors.toList());
    }

    public Long create(PetRequest petRequest) throws IOException {
        Pet pet = new Pet(
                petRequest.petName(),
                petRequest.breed(),
                petRequest.birthDate(),
                petRequest.profileImg() != null ? petRequest.profileImg().getBytes() : null,
                petRequest.gender(),
                memberRepository.findById(petRequest.memberId()).orElseThrow()
                /* Fetch the member from the database using memberId */
                // memberRepository.findById(petRequest.memberId()).orElseThrow(...)
        );
        petRepository.save(pet);
        return pet.getId();
    }

    // Method to get the pet entity by id
    public Pet getEntity(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pet not found with id: " + id));
    }

    // Method to update the pet
    public void update(Long id, PetRequest petRequest, MultipartFile profileImg) throws IOException {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pet not found with id: " + id));

        pet.setPetName(petRequest.petName());
        pet.setBreed(petRequest.breed());
        pet.setBirthDate(petRequest.birthDate());
        pet.setGender(petRequest.gender());
        if (profileImg != null) {
            pet.setProfileImg(profileImg.getBytes());
        }
        petRepository.save(pet);
    }

    public void delete(Long id) {
        petRepository.deleteById(id);
    }
}
