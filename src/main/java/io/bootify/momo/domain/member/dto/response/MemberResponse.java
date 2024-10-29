package io.bootify.momo.domain.member.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponse {
    private Long id;
    private String username;
    private String contact;
    private String googleId;
    private String profileImg; // Base64 인코딩된 이미지 URL 형태

    // 필요에 따라 기본 생성자, 파라미터 생성자 추가 가능
}
