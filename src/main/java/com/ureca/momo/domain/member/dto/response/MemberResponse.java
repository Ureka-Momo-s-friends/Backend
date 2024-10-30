package com.ureca.momo.domain.member.dto.response;

import com.ureca.momo.domain.member.model.Member;

public record MemberResponse(
        Long id,
        String username,
        String contact,
        String googleId,
        String profileImg
) {
    public static MemberResponse of(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getUsername(),
                member.getContact(),
                member.getGoogleId(),
                member.getProfileImg() != null
                        ? new String(java.util.Base64.getEncoder().encode(member.getProfileImg()))
                        : null        );
    }
}
