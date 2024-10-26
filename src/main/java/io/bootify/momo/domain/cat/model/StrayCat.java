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
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class StrayCat {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String catImgUrl;

    // 위도
    @Column(nullable = false, precision = 18, scale = 8)
    private BigDecimal lat;

    // 경도
    @Column(nullable = false, precision = 19, scale = 8)
    private BigDecimal lon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public StrayCat(String catImgUrl, BigDecimal lat, BigDecimal lon, Member member) {
        this.catImgUrl = catImgUrl;
        this.lat = lat;
        this.lon = lon;
        this.member = member;
    }
}
