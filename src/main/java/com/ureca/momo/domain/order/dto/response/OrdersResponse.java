package com.ureca.momo.domain.order.dto.response;


import com.ureca.momo.domain.order.model.OrderStatus;
import com.ureca.momo.domain.order.model.Orders;

import java.time.LocalDateTime;

public record OrdersResponse(
        Long orderId,
        LocalDateTime orderTime,
        String orderName,
        String orderThumbnail,
        OrderStatus orderStatus,
        Integer amount  //금액 추가
) {
    public static OrdersResponse of(final Orders orders) {
        return new OrdersResponse(
                orders.getId(),
                orders.getOrderTime(),
                orders.getOrderName(),
                orders.getOrderThumbnail(),
                orders.getStatus(),
                orders.getPay() != null ? orders.getPay().getAmount() : 0 // Pay 엔티티에서 금액 가져오기
        );
    }
}
