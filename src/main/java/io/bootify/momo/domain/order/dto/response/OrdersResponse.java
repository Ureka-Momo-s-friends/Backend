package io.bootify.momo.domain.order.dto.response;

import io.bootify.momo.domain.order.model.Order;
import io.bootify.momo.domain.order.model.OrderStatus;

import java.time.LocalDateTime;

public record OrdersResponse(
        Long orderId,
        LocalDateTime orderTime,
        String orderName,
        String orderThumbnail,
        OrderStatus orderStatus
) {
    public static OrdersResponse of(final Order order) {
        return new OrdersResponse(
                order.getId(),
                order.getOrderTime(),
                order.getOrderName(),
                order.getOrderThumbnail(),
                order.getStatus()
        );
    }
}
