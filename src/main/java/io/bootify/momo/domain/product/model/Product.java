package io.bootify.momo.domain.product.model;

import io.bootify.momo.domain.member.model.Cart;
import io.bootify.momo.domain.order.model.OrderDetail;
import jakarta.persistence.*;

import java.util.Set;
import lombok.Getter;


@Entity
@Getter
public class Product {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer salePrice;

    @Column(length = 500)
    private String thumbnail;

    @Column(length = 500)
    private String detail;

    @OneToMany(mappedBy = "product")
    private Set<Cart> productCarts;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "prodcut")
    private Set<OrderDetail> prodcutOrderDetails;

}
