package com.ureca.momo.domain.member.repository;

import com.ureca.momo.domain.member.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c " +
            "JOIN FETCH c.product p " +
            "WHERE c.member.id = :memberId")
    List<Cart> findAllByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT c FROM Cart c " +
            "JOIN FETCH c.product p " +
            "WHERE c.member.id = :memberId " +
            "AND c.product.id IN :productIds")
    List<Cart> findByMemberIdAndProductIdIn(
            @Param("memberId") Long memberId,
            @Param("productIds") List<Long> productIds
    );

    @Modifying
    @Query("DELETE FROM Cart c WHERE c.member.id = :memberId")
    void deleteAllByMemberId(@Param("memberId") Long memberId);
}
