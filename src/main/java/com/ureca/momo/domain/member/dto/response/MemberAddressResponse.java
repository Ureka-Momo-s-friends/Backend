package com.ureca.momo.domain.member.dto.response;


import com.ureca.momo.domain.member.model.MemberAddress;

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
                memberAddress.getAddress(),
                memberAddress.getAddressDetail(),
                memberAddress.getAddressName(),
                memberAddress.getAddressContact()
        );
    }
}
