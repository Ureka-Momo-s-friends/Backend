package com.ureca.momo.domain.member.model;

import com.ureca.momo.domain.member.dto.request.MemberAddressRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Entity
@Getter
@NoArgsConstructor
public class MemberAddress {

    @Id
    @Column(nullable = false, updatable = false, name = "member_address_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer zonecode;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(length = 50)
    private String addressDetail;

    @Column(nullable = false, length = 20)
    private String addressName;

    @Column(nullable = false, length = 11)
    private String addressContact;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;


    public MemberAddress(MemberAddressRequest request, Member member) {
        this.zonecode = request.zonecode();
        this.address = request.addresss();
        this.addressDetail = request.addressDetail();
        this.addressName = request.addressName();
        this.addressContact = request.addressContact();
        this.member = member;
    }

    public void update(MemberAddressRequest request) {
        if (request.zonecode() != null) {
            this.zonecode = request.zonecode();
        }
        if (StringUtils.hasText(request.addresss())) {
            this.address = request.addresss();
        }
        if (StringUtils.hasText(request.addressDetail())) {
            this.addressDetail = request.addressDetail();
        }
        if (StringUtils.hasText(request.addressName())) {
            this.addressName = request.addressName();
        }
        if (StringUtils.hasText(request.addressContact())) {
            this.addressContact = request.addressContact();
        }
    }

}
