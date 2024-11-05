package com.ureca.momo.domain.pay.service;

import com.ureca.momo.domain.pay.model.Pay;
import com.ureca.momo.domain.pay.repository.PayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PayService {

    @Autowired
    private PayRepository payRepository;

    // 결제 정보 저장
    public Pay savePayment(Pay pay) {
        if (payRepository.findByPaymentKey(pay.getPaymentKey()).isPresent()) {
            throw new RuntimeException("이미 저장된 결제입니다.");
        }
        pay.setPaymentDate(LocalDateTime.now()); // 결제 일시 설정
        return payRepository.save(pay);
    }

    // 특정 주문 ID로 결제 정보 조회
    public Optional<Pay> findPaymentByOrderId(Long orderId) {
        return Optional.ofNullable(payRepository.findFirstByOrdersId(orderId));
    }

    // 모든 결제 정보 가져오기
    public List<Pay> getAllPayments() {
        return payRepository.findAll();
    }

    // 결제 취소 메서드
    public String cancelPayment(String paymentKey) {
        Optional<Pay> optionalPay = payRepository.findByPaymentKey(paymentKey);
        if (optionalPay.isPresent()) {
            Pay pay = optionalPay.get();
            pay.setStatus("CANCELLED");
            payRepository.save(pay);
            return "결제가 취소되었습니다.";
        } else {
            throw new IllegalArgumentException("결제 정보가 존재하지 않습니다.");
        }
    }

    // 결제 정보를 ID로 조회하는 메서드
    public Optional<Pay> findPaymentById(Long payId) {
        return payRepository.findById(payId);
    }
}
