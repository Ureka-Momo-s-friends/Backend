package io.bootify.momo.domain.cat.model;

import io.bootify.momo.domain.member.model.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Pet {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15)
    private String petName;

    @Column
    private LocalDate birthDate;

    @Column(length = 500)
    private String profileImgUrl;

    @Column(nullable = false, columnDefinition = "tinyint", length = 1)
    private Boolean gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = true) // 변경된 부분: nullable = true
    private Member member;

    public Pet(String petName, LocalDate birthDate, String profileImgUrl, Boolean gender, Member member) {
        this.petName = petName;
        this.birthDate = birthDate;
        this.profileImgUrl = profileImgUrl;
        this.gender = gender;
        this.member = member;
    }
}
