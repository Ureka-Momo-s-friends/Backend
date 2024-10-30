package com.ureca.momo.domain.cat.dto.response;

import com.ureca.momo.domain.cat.model.Pet;

import java.time.LocalDate;

public record PetResponse(
        Long id,
        String petName,
        String breed,
        LocalDate birthDate,
        Boolean gender,
        byte[] profileImg // 이미지 데이터 필드 추가
) {
    public static PetResponse of(Pet pet) {
        return new PetResponse(
                pet.getId(),
                pet.getPetName(),
                pet.getBreed(),
                pet.getBirthDate(),
                pet.getGender(),
                pet.getProfileImg()
        );
    }
}