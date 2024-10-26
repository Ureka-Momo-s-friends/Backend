package io.bootify.momo.domain.member.dto.response;

import io.bootify.momo.domain.member.model.MemberAddress;
import io.bootify.momo.domain.order.model.Order;

import java.util.Set;

public record MemberAddressResponse(
        Long id,
        Integer zonecode,
        String addresss,
        String addressDetail,
        String addressName,
        String addressContact,
        Set<Order> addressOrders,
        Boolean isFirst
) {

    public static MemberAddressResponse of(MemberAddress memberAddress) {
        return new MemberAddressResponse(
                memberAddress.getId(),
                memberAddress.getZonecode(),
                memberAddress.getAddresss(),
                memberAddress.getAddressDetail(),
                memberAddress.getAddressName(),
                memberAddress.getAddressContact(),
                memberAddress.getAddressOrders(),
                memberAddress.getIsFirst()
        );
    }
}
