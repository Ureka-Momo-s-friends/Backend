package io.bootify.momo.domain.order.dto.request;

import java.util.List;

public record OrderRequest(
        Long memberId,
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
