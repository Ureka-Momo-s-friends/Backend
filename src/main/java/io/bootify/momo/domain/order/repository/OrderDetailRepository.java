package io.bootify.momo.domain.order.repository;

import io.bootify.momo.domain.order.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    List<OrderDetail> findAllByOrderId(Long orderId);
}
