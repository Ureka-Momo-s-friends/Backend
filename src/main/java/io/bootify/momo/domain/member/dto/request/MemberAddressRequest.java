package io.bootify.momo.domain.member.dto.request;

public record MemberAddressRequest(
        Integer zonecode,
        String addresss,
        String addressDetail,
        String addressName,
        String addressContact
) {
}
