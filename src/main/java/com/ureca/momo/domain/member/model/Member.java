package com.ureca.momo.domain.member.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 열 이름을 명시적으로 지정하지 않습니다.

    @Column(nullable = false, length = 15)
    private String username;

    @Column(nullable = false, length = 11)
    private String contact;

    @Column(name = "google_id", length = 255, nullable = false)
    private String googleId;

    @Lob
    @Column(name = "profile_img", columnDefinition = "LONGBLOB")
    private byte[] profileImg;

    public Member(String username, String contact, String googleId, byte[] profileImg) {
        this.username = username;
        this.contact = contact;
        this.googleId = googleId;
        this.profileImg = profileImg;
    }
}

