package io.bootify.momo.repos;

import io.bootify.momo.domain.Cart;
import io.bootify.momo.domain.Member;
import io.bootify.momo.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findFirstByMember(Member member);

    Cart findFirstByProduct(Product product);

}
