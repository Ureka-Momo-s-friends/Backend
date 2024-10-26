package io.bootify.momo.domain.member.model;

import io.bootify.momo.domain.product.model.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_cart_member_product",  // unique key 이름
                columnNames = {"member_id", "product_id"}  // 유니크 제약조건을 걸 컬럼들
        )
})
@NoArgsConstructor
public class Cart {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Min(value = 1, message = "수량은 1개 이상이어야 합니다")
    private Integer amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public void updateAmount(Integer diff) {
        this.amount += diff;
    }

    public Cart(Integer amount, Member member, Product product) {
        this.amount = amount;
        this.member = member;
        this.product = product;
    }

}
