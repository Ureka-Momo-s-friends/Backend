package com.ureca.momo.domain.order.model;

import com.ureca.momo.domain.member.model.Member;
import com.ureca.momo.domain.pay.model.Pay;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor
public class Orders {

    @Id
    @Column(nullable = false, updatable = false, name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime orderTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private Integer zonecode;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(length = 50)
    private String addressDetail;

    @OneToOne(mappedBy = "orders", cascade = CascadeType.ALL)
    private Pay pay;

    private String orderName;

    private String orderThumbnail;

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }

    public Orders(String orderThumbnail, String orderName, Pay pay, String addressDetail, String address, Integer zonecode, Member member, OrderStatus status, LocalDateTime orderTime) {
        this.orderThumbnail = orderThumbnail;
        this.orderName = orderName;
        this.pay = pay;
        this.addressDetail = addressDetail;
        this.address = address;
        this.zonecode = zonecode;
        this.member = member;
        this.status = status;
        this.orderTime = orderTime;
    }
}
