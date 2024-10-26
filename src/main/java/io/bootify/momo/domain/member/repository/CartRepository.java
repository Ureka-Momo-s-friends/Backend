package io.bootify.momo.domain.member.repository;

import io.bootify.momo.domain.member.model.Cart;
import io.bootify.momo.domain.member.model.Member;
import io.bootify.momo.domain.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findFirstByMember(Member member);

    Cart findFirstByProduct(Product product);

}
