package com.ureca.momo.domain.order.model;

import com.ureca.momo.domain.product.model.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class OrderDetail {

    @Id
    @Column(name = "order_detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public OrderDetail(Integer amount, Orders orders, Product product) {
        this.amount = amount;
        this.orders = orders;
        this.product = product;
    }

    public Integer calculatePrice() {
        return amount * product.getPrice();
    }
}
