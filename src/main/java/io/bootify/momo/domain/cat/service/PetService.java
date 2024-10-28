package io.bootify.momo.domain.cat.service;

import io.bootify.momo.domain.cat.dto.request.PetRequest;
import io.bootify.momo.domain.cat.dto.response.PetResponse;
import io.bootify.momo.domain.cat.model.Pet;
import io.bootify.momo.domain.cat.repository.PetRepository;
import io.bootify.momo.domain.member.model.Member;
import io.bootify.momo.domain.member.repository.MemberRepository;
import io.bootify.momo.util.NotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final MemberRepository memberRepository;

    public PetService(final PetRepository petRepository, final MemberRepository memberRepository) {
        this.petRepository = petRepository;
        this.memberRepository = memberRepository;
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

    public Long create(PetRequest petRequest) throws IOException {
        Member member = memberRepository.findById(petRequest.memberId()).orElseThrow(NotFoundException::new);
        Pet pet = mapToEntity(petRequest, member);
        petRepository.save(pet);
        return pet.getId();
    }

    public void update(Long id, PetRequest petRequest) throws IOException {
        Pet pet = petRepository.findById(id).orElseThrow(NotFoundException::new);
        mapToEntity(petRequest, pet);
        petRepository.save(pet);
    }

    public void delete(Long id) {
        petRepository.deleteById(id);
    }

    public Pet getEntity(Long id) {
        return petRepository.findById(id).orElseThrow(() -> new NotFoundException("Pet not found with id: " + id));
    }


    // PetService.java
    private Pet mapToEntity(PetRequest petRequest, Member member) throws IOException {
        Pet pet = new Pet();
        pet.setPetName(petRequest.petName());
        pet.setBirthDate(petRequest.birthDate());
        pet.setBreed(petRequest.breed());
        pet.setGender(petRequest.gender());
        pet.setMember(member);
        if (petRequest.profileImg() != null) {
            pet.setProfileImg(petRequest.profileImg().getBytes());
        }
        return pet;
    }

    private void mapToEntity(PetRequest petRequest, Pet pet) throws IOException {
        pet.setPetName(petRequest.petName());
        pet.setBirthDate(petRequest.birthDate());
        pet.setBreed(petRequest.breed());
        pet.setGender(petRequest.gender());
        if (petRequest.profileImg() != null) {
            pet.setProfileImg(petRequest.profileImg().getBytes());
        }
    }
}
