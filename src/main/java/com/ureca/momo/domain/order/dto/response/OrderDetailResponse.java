package com.ureca.momo.domain.order.dto.response;

import com.ureca.momo.domain.order.model.OrderDetail;

public record OrderDetailResponse(
        String thumbnail,
        String productName,
        Integer totalPrice,
        Integer amount
) {
    public static OrderDetailResponse of(OrderDetail orderDetail) {
        return new OrderDetailResponse(
                orderDetail.getProduct().getThumbnail(),
                orderDetail.getProduct().getName(),
                orderDetail.calculatePrice(),
                orderDetail.getAmount()
        );
    }
}
