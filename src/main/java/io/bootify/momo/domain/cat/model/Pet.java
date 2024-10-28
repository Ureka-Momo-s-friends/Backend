package io.bootify.momo.domain.cat.model;

import io.bootify.momo.domain.member.model.Member;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, length = 15)
    private String petName; // 고양이 이름

    @Column(length = 20)
    private String breed; // 고양이 품종

    @Column
    private LocalDate birthDate; // 생일

    @Column(length = 500)
    private String profileImgUrl; // 프로필 이미지 URL

    @Column(nullable = false, columnDefinition = "tinyint(1)")
    private Boolean gender; // 성별

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = true) // 회원 ID 외래 키
    private Member member; // 회원과의 관계

    // 사용자 정의 생성자
    public Pet(String petName, String breed, LocalDate birthDate, String profileImgUrl, Boolean gender, Member member) {
        this.petName = petName;
        this.breed = breed;
        this.birthDate = birthDate;
        this.profileImgUrl = profileImgUrl;
        this.gender = gender;
        this.member = member;
    }
}
