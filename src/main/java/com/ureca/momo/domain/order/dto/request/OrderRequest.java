package com.ureca.momo.domain.order.dto.request;

import java.util.List;

public record OrderRequest(
        Integer zonecode,
        String address,
        String addressDetail,
        List<OrderDetailRequest> orderDetailRequestList
) {
    public record OrderDetailRequest(
            Long productId,
            Integer amount
    ) {
    }
}
