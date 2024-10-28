package io.bootify.momo.domain.cat.dto.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;

public record PetRequest(
        @NotNull String petName,
        String breed,
        LocalDate birthDate,
        Boolean gender,
        MultipartFile profileImage,
        Long memberId // Member ID 추가
) {
}
