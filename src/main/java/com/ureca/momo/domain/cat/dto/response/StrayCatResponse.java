package com.ureca.momo.domain.cat.dto.response;

import com.ureca.momo.domain.cat.model.StrayCat;

public record StrayCatResponse(
        Long id,
        String catImgUrl,
        Double lat,
        Double lon
) {
    public static StrayCatResponse of(final StrayCat strayCat) {
        return new StrayCatResponse(
                strayCat.getId(),
                strayCat.getCatImgUrl(),
                strayCat.getLat(),
                strayCat.getLon()
        );
    }
}
