package com.ureca.momo.domain.order.repository;

import com.ureca.momo.domain.order.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    List<OrderDetail> findAllByOrdersId(Long orderId);
}
