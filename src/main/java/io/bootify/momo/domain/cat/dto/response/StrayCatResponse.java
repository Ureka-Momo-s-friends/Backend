package io.bootify.momo.domain.cat.dto.response;

import io.bootify.momo.domain.cat.model.StrayCat;

import java.math.BigDecimal;

public record StrayCatResponse(
        Long id,
        String catImgUrl,
        BigDecimal lat,
        BigDecimal lon
) {
    public static StrayCatResponse of(StrayCat strayCat) {
        return new StrayCatResponse(
                strayCat.getId(),
                strayCat.getCatImgUrl(),
                strayCat.getLat(),
                strayCat.getLon()
        );
    }
}
