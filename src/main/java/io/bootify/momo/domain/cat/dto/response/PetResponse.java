package io.bootify.momo.domain.cat.dto.response;

import io.bootify.momo.domain.cat.model.Pet;
import java.time.LocalDate;

public record PetResponse(
        Long id,
        String petName,
        LocalDate birthDate,
        String breed,
        Boolean gender,
        byte[] profileImg // 이미지 데이터를 전달
) {
    public static PetResponse of(Pet pet) {
        return new PetResponse(
                pet.getId(),
                pet.getPetName(),
                pet.getBirthDate(),
                pet.getBreed(),
                pet.getGender(),
                pet.getProfileImg() // 이미지 데이터
        );
    }
}
