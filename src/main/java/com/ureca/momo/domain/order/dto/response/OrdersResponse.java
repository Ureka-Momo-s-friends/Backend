package com.ureca.momo.domain.order.dto.response;


import com.ureca.momo.domain.order.model.OrderStatus;
import com.ureca.momo.domain.order.model.Orders;

import java.time.LocalDateTime;

public record OrdersResponse(
        Long orderId,
        LocalDateTime orderTime,
        String orderName,
        String orderThumbnail,
        OrderStatus orderStatus
) {
    public static OrdersResponse of(final Orders orders) {
        return new OrdersResponse(
                orders.getId(),
                orders.getOrderTime(),
                orders.getOrderName(),
                orders.getOrderThumbnail(),
                orders.getStatus()
        );
    }
}
