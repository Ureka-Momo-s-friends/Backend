package com.ureca.momo.domain.order.dto.request;

public record OrderRequest(
        Long memberId,
        String address,
        String paymentKey
) {
}
