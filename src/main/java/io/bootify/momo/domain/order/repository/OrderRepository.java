package io.bootify.momo.domain.order.repository;

import io.bootify.momo.domain.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByMemberId(Long memberId);

}
