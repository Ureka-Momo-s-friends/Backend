package com.ureca.momo.domain.cat.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

// PetRequest.java
public record PetRequest(
        String petName,
        String breed,
        LocalDate birthDate,
        Boolean gender,
        Long memberId,
        MultipartFile profileImg // 필드 이름을 profileImg로 설정
) {
}
