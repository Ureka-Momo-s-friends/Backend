package com.ureca.momo.domain.product.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
public class Product {

    @Id
    @Column(nullable = false, updatable = false, name = "product_id")
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

    @Column(length = 1000)
    private String detail;

    @Enumerated(EnumType.STRING)
    private Category category;

}
