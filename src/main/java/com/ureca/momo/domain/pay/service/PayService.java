package com.ureca.momo.domain.pay.service;

import com.ureca.momo.domain.pay.model.Pay;
import com.ureca.momo.domain.pay.repository.PayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.List;
import java.util.Map;
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
        return payRepository.save(pay);
    }

    // 특정 주문 ID로 결제 정보 조회
    public Optional<Pay> findPaymentByOrderId(Long orderId) {
        return Optional.ofNullable(payRepository.findFirstByOrdersId(orderId));
    }

    public List<Pay> getAllPayments() {
        return payRepository.findAll();
    }

    @Value("${toss.secret-key}")
    private String secretKey; // application.properties에 설정된 시크릿 키를 사용

    // 결제 상태를 'CANCELLED'로 업데이트하는 메서드
    public String cancelPayment(String paymentKey) {
        // paymentKey로 결제 정보 찾기
        Optional<Pay> optionalPay = payRepository.findByPaymentKey(paymentKey);
        if (optionalPay.isPresent()) {
            Pay pay = optionalPay.get();
            // 결제 상태를 CANCELLED로 변경
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
//
//    private final PayRepository payRepository;
//    private final OrderRepository orderRepository;
//
//    public PayService(final PayRepository payRepository, final OrderRepository orderRepository) {
//        this.payRepository = payRepository;
//        this.orderRepository = orderRepository;
//    }
//
//    public List<PayDTO> findAll() {
//        final List<Pay> pays = payRepository.findAll(Sort.by("id"));
//        return pays.stream()
//                .map(pay -> mapToDTO(pay, new PayDTO()))
//                .toList();
//    }
//
//    public PayDTO get(final Long id) {
//        return payRepository.findById(id)
//                .map(pay -> mapToDTO(pay, new PayDTO()))
//                .orElseThrow(NotFoundException::new);
//    }
//
//    public Long create(final PayDTO payDTO) {
//        final Pay pay = new Pay();
//        mapToEntity(payDTO, pay);
//        return payRepository.save(pay).getId();
//    }
//
//    public void update(final Long id, final PayDTO payDTO) {
//        final Pay pay = payRepository.findById(id)
//                .orElseThrow(NotFoundException::new);
//        mapToEntity(payDTO, pay);
//        payRepository.save(pay);
//    }
//
//    public void delete(final Long id) {
//        payRepository.deleteById(id);
//    }
//
//    private PayDTO mapToDTO(final Pay pay, final PayDTO payDTO) {
//        payDTO.setId(pay.getId());
//        payDTO.setAmount(pay.getAmount());
//        payDTO.setStatus(pay.getStatus());
//        payDTO.setPaymentKey(pay.getPaymentKey());
//        payDTO.setOrder(pay.getOrder() == null ? null : pay.getOrder().getId());
//        return payDTO;
//    }
//
//    private Pay mapToEntity(final PayDTO payDTO, final Pay pay) {
//        pay.setAmount(payDTO.getAmount());
//        pay.setStatus(payDTO.getStatus());
//        pay.setPaymentKey(payDTO.getPaymentKey());
//        final Order order = payDTO.getOrder() == null ? null : orderRepository.findById(payDTO.getOrder())
//                .orElseThrow(() -> new NotFoundException("order not found"));
//        pay.setOrder(order);
//        return pay;
//    }


