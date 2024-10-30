package com.ureca.momo.domain.pay.model;

import com.ureca.momo.domain.order.model.Orders;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
public class Pay {

    @Id
    @Column(nullable = false, updatable = false, name = "pay_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false, length = 10)
    private String status;

    @Column(nullable = false, length = 200)
    private String paymentKey;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders orders;

}