package com.ureca.momo.domain.member.dto.request;

public record MemberRequest(
        String username,
        String contact,
        String googleId,
        byte[] profileImg
) {
}
