package io.bootify.momo.domain.order.model;

import io.bootify.momo.domain.product.model.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
public class OrderDetail {

    @Id
    @Column(nullable = false, updatable = false, name = "order_detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public OrderDetail(Integer amount, Order order, Product product) {
        this.amount = amount;
        this.order = order;
        this.product = product;
    }

    public Integer calculatePrice() {
        return amount * product.getPrice();
    }
}
