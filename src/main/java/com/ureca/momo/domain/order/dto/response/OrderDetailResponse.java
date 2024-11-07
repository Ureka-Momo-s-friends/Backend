package com.ureca.momo.domain.order.dto.response;

import com.ureca.momo.domain.order.model.OrderDetail;

public record OrderDetailResponse(
        String thumbnail,
        Long productId,
        String productName,
        Integer totalPrice,
        Integer amount
) {
    public static OrderDetailResponse of(OrderDetail orderDetail) {
        return new OrderDetailResponse(
                orderDetail.getProduct().getThumbnail(),
                orderDetail.getProduct().getId(),
                orderDetail.getProduct().getName(),
                orderDetail.calculatePrice(),
                orderDetail.getAmount()
        );
    }
}
