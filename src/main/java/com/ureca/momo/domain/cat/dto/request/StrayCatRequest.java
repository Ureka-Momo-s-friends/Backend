package com.ureca.momo.domain.cat.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record StrayCatRequest(
        MultipartFile catImg,
        Double lat,
        Double lon
) {
}
