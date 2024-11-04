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
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15)
    private String username;

    @Column(nullable = false, length = 11)
    private String contact;

    @Column(name = "google_id", length = 255, nullable = false)
    private String googleId;

    @Lob // BLOB 타입으로 지정
    @Column(name = "profile_img", columnDefinition = "LONGBLOB")
    private byte[] profileImg; // 변경된 필드명과 타입

    public Member(String username, String contact, String googleId, byte[] profileImg) {
        this.username = username;
        this.contact = contact;
        this.googleId = googleId;
        this.profileImg = profileImg;
    }
}
