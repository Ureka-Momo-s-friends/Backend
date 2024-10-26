package io.bootify.momo.domain.member.dto.response;

import io.bootify.momo.domain.member.model.MemberAddress;

public record MemberAddressResponse(
        Long id,
        Integer zonecode,
        String addresss,
        String addressDetail,
        String addressName,
        String addressContact
) {

    public static MemberAddressResponse of(MemberAddress memberAddress) {
        return new MemberAddressResponse(
                memberAddress.getId(),
                memberAddress.getZonecode(),
                memberAddress.getAddresss(),
                memberAddress.getAddressDetail(),
                memberAddress.getAddressName(),
                memberAddress.getAddressContact()
        );
    }
}
