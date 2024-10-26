package io.bootify.momo.domain.member.model;

import io.bootify.momo.domain.order.model.Order;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
public class MemberAddress {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer zonecode;

    @Column(nullable = false, length = 100)
    private String addresss;

    @Column(length = 50)
    private String addressDetail;

    @Column(nullable = false, length = 20)
    private String addressName;

    @Column(nullable = false, length = 11)
    private String addressContact;

    @OneToMany(mappedBy = "address")
    private Set<Order> addressOrders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

}
