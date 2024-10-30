package com.ureca.momo.domain.pay.repository;

import com.ureca.momo.domain.pay.model.Pay;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PayRepository extends JpaRepository<Pay, Long> {

    Pay findFirstByOrdersId(Long orderId);

}
