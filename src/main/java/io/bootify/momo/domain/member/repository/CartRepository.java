package io.bootify.momo.domain.member.repository;

import io.bootify.momo.domain.member.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByMemberId(Long memberId);
    List<Cart> findByMemberIdAndProductIdIn(Long memberId, List<Long> productIds);
}