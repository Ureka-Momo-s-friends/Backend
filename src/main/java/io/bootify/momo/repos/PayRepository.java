package io.bootify.momo.repos;

import io.bootify.momo.domain.Order;
import io.bootify.momo.domain.Pay;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PayRepository extends JpaRepository<Pay, Long> {

    Pay findFirstByOrder(Order order);

}
