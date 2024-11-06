package com.ureca.momo.domain.pay.repository;

import com.ureca.momo.domain.pay.model.Pay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface PayRepository extends JpaRepository<Pay, Long> {

    Pay findFirstByOrdersId(Long orderId);

    Optional<Pay> findByPaymentKey(String paymentKey);

}
