package com.ureca.momo.domain.order.dto.request;

import java.util.List;

public record OrderRequest(
        Long memberId, // 추가된 부분
        Integer zonecode,
        String address,
        String addressDetail,
        String paymentKey, // 추가된 부분
        List<OrderDetailRequest> orderDetailRequestList
) {
    public record OrderDetailRequest(
            Long productId,
            Integer amount
    ) {
    }
}
