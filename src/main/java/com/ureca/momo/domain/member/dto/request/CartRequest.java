package com.ureca.momo.domain.member.dto.request;

public record CartRequest(
        Integer amount,
        Long productId
) {
}
