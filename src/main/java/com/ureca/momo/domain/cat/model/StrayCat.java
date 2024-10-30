package com.ureca.momo.domain.cat.model;

import com.ureca.momo.domain.member.model.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class StrayCat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String catImgUrl;

    // 위도
    private Double lat;

    // 경도
    private Double lon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public StrayCat(String catImgUrl, Double lat, Double lon, Member member) {
        this.catImgUrl = catImgUrl;
        this.lat = lat;
        this.lon = lon;
        this.member = member;
    }
}
