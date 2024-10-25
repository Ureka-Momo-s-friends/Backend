package io.bootify.momo.service;

import io.bootify.momo.domain.Member;
import io.bootify.momo.domain.Pet;
import io.bootify.momo.model.PetDTO;
import io.bootify.momo.repos.MemberRepository;
import io.bootify.momo.repos.PetRepository;
import io.bootify.momo.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final MemberRepository memberRepository;

    public PetService(final PetRepository petRepository, final MemberRepository memberRepository) {
        this.petRepository = petRepository;
        this.memberRepository = memberRepository;
    }

    public List<PetDTO> findAll() {
        final List<Pet> pets = petRepository.findAll(Sort.by("id"));
        return pets.stream()
                .map(pet -> mapToDTO(pet, new PetDTO()))
                .toList();
    }

    public List<PetDTO> getPetsByMemberId(Long memberId) {
        List<Pet> pets = petRepository.findByMemberId(memberId);
        return pets.stream()
                .map(pet -> mapToDTO(pet, new PetDTO()))
                .toList();
    }

    public PetDTO get(final Long id) {
        return petRepository.findById(id)
                .map(pet -> mapToDTO(pet, new PetDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PetDTO petDTO) {
        final Pet pet = new Pet();
        mapToEntity(petDTO, pet);
        return petRepository.save(pet).getId();
    }

    public void update(final Long id, final PetDTO petDTO) {
        final Pet pet = petRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(petDTO, pet);
        petRepository.save(pet);
    }

    public void delete(final Long id) {
        petRepository.deleteById(id);
    }

    private PetDTO mapToDTO(final Pet pet, final PetDTO petDTO) {
        petDTO.setId(pet.getId());
        petDTO.setPetName(pet.getPetName());
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setProfileImgUrl(pet.getProfileImgUrl());
        petDTO.setGender(pet.getGender());
        petDTO.setMember(pet.getMember() == null ? null : pet.getMember().getId());
        return petDTO;
    }

    private Pet mapToEntity(final PetDTO petDTO, final Pet pet) {
        pet.setPetName(petDTO.getPetName());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setProfileImgUrl(petDTO.getProfileImgUrl());
        pet.setGender(petDTO.getGender());
        final Member member = petDTO.getMember() == null ? null : memberRepository.findById(petDTO.getMember())
                .orElseThrow(() -> new NotFoundException("member not found"));
        pet.setMember(member);
        return pet;
    }

}
