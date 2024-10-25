package io.bootify.momo.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {

    private Long id;

    @NotNull
    @Size(max = 15)
    private String username;

    @NotNull
    @Size(max = 11)
    private String contact;

    @NotNull
    @Size(max = 50) // 구글 ID의 최대 길이 설정
    private String googleId;
}
