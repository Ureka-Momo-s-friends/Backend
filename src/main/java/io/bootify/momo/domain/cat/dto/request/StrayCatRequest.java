package io.bootify.momo.domain.cat.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public record StrayCatRequest(
        MultipartFile catImg,
        BigDecimal lat,
        BigDecimal lon
) {
}
