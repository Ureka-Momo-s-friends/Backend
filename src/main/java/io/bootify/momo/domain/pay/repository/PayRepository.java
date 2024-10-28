package io.bootify.momo.domain.pay.repository;

import io.bootify.momo.domain.order.model.Order;
import io.bootify.momo.domain.pay.model.Pay;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PayRepository extends JpaRepository<Pay, Long> {

    Pay findFirstByOrder(Order order);

}
