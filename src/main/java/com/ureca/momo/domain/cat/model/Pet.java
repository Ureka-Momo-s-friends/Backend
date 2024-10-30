package com.ureca.momo.domain.cat.model;

import com.ureca.momo.domain.member.model.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pet {

    @Id
    @Column(name = "pet_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15)
    private String petName;

    @Column(length = 20)
    private String breed;

    @Column
    private LocalDate birthDate;

    @Lob
    @Column(name = "profile_img", columnDefinition = "LONGBLOB")
    private byte[] profileImg;

    @Column(nullable = false, columnDefinition = "tinyint(1)")
    private Boolean gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public Pet(String petName, String breed, LocalDate birthDate, byte[] profileImg, Boolean gender, Member member) {
        this.petName = petName;
        this.breed = breed;
        this.birthDate = birthDate;
        this.profileImg = profileImg;
        this.gender = gender;
        this.member = member;
    }
}
