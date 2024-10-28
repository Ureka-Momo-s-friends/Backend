package io.bootify.momo.domain.cat.dto.response;

import io.bootify.momo.domain.cat.model.Pet;
import java.time.LocalDate;

public record PetResponse(
        Long id,
        String petName,
        LocalDate birthDate,
        String breed,
        Boolean gender, // true = 암컷, false = 수컷
        String profileImgUrl // 이미지 파일 URL
) {
    public static PetResponse of(Pet pet) {
        return new PetResponse(
                pet.getId(),
                pet.getPetName(),
                pet.getBirthDate(),
                pet.getBreed(),
                pet.getGender(),
                pet.getProfileImgUrl()
        );
    }
}
