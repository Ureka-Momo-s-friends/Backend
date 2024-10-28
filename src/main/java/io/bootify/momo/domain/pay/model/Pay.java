package io.bootify.momo.domain.pay.model;

import io.bootify.momo.domain.order.model.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
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
    private Order order;

}
