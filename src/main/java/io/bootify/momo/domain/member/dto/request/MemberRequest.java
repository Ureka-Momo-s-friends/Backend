package io.bootify.momo.domain.member.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequest {
    private String username;
    private String contact;
    private String googleId;
    private byte[] profileImg; // 이미지 데이터가 포함될 필드

    // 필요에 따라 기본 생성자, 파라미터 생성자 추가 가능
}
