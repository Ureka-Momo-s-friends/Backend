package com.ureca.momo.domain.order.repository;

import com.ureca.momo.domain.order.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderRepository extends JpaRepository<Orders, Long> {

    List<Orders> findByMemberIdOrderByOrderTimeDesc(Long memberId);
}
