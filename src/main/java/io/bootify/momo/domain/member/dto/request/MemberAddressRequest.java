package io.bootify.momo.domain.member.dto.request;

import io.bootify.momo.domain.member.model.MemberAddress;
import io.bootify.momo.domain.order.model.Order;
import java.util.Set;

public record MemberAddressRequest(
        Integer zonecode,
        String addresss,
        String addressDetail,
        String addressName,
        String addressContact,
        Set<Order> addressOrders
) {
}
