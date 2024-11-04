package com.ureca.momo.domain.pay.controller;

import com.ureca.momo.domain.pay.model.Pay;
import com.ureca.momo.domain.pay.repository.PayRepository;
import com.ureca.momo.domain.pay.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/api/pays", produces = MediaType.APPLICATION_JSON_VALUE)
public class PayController {

    @Autowired
    private PayRepository payRepository;
    @Autowired
    private PayService payService;

    // 결제 정보 저장
    @PostMapping
    public ResponseEntity<Pay> createPayment(@RequestBody Pay pay) {
        Pay savedPay = payRepository.save(pay);
        return ResponseEntity.ok(savedPay);
    }

    // 특정 주문 ID로 결제 정보 검색
    @GetMapping("/{orderId}")
    public ResponseEntity<Pay> getPaymentByOrderId(@PathVariable Long orderId) {
        Pay pay = payRepository.findFirstByOrdersId(orderId);
        if (pay != null) {
            return ResponseEntity.ok(pay);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //결제 내역 가져오기
    @GetMapping("/all")
    public ResponseEntity<List<Pay>> getAllPayments() {
        List<Pay> payments = payService.getAllPayments();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    //결제 성공 시 데이터 저장
    @PostMapping("/save")
    public ResponseEntity<String> savePayment(@RequestBody Pay pay) {
        if (payRepository.findByPaymentKey(pay.getPaymentKey()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 저장된 결제 정보입니다.");
        }
        payRepository.save(pay);
        return ResponseEntity.ok("결제 정보가 저장되었습니다.");
    }

    // 추가: 결제 취소 엔드포인트
    @PostMapping("/cancel")
    public ResponseEntity<String> cancelPayment(@RequestBody Map<String, String> request) {
        String paymentKey = request.get("paymentKey");
        if (paymentKey == null || paymentKey.isEmpty()) {
            return ResponseEntity.badRequest().body("결제 키가 누락되었습니다.");
        }
        try {
            String message = payService.cancelPayment(paymentKey);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{paymentKey}")
    public ResponseEntity<String> deletePaymentByPaymentKey(@PathVariable String paymentKey) {
        Optional<Pay> payOptional = payRepository.findByPaymentKey(paymentKey);

        if (payOptional.isPresent()) {
            payRepository.delete(payOptional.get());
            return ResponseEntity.ok("결제 정보가 성공적으로 삭제되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 결제 키에 대한 결제 정보가 없습니다.");
        }
    }

}
//
//    private final PayService payService;
//
//    public PayResource(final PayService payService) {
//        this.payService = payService;
//    }
//
//    @GetMapping
//    public ResponseEntity<List<PayDTO>> getAllPays() {
//        return ResponseEntity.ok(payService.findAll());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<PayDTO> getPay(@PathVariable(name = "id") final Long id) {
//        return ResponseEntity.ok(payService.get(id));
//    }
//
//    @PostMapping
//    public ResponseEntity<Long> createPay(@RequestBody @Valid final PayDTO payDTO) {
//        final Long createdId = payService.create(payDTO);
//        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Long> updatePay(@PathVariable(name = "id") final Long id,
//            @RequestBody @Valid final PayDTO payDTO) {
//        payService.update(id, payDTO);
//        return ResponseEntity.ok(id);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deletePay(@PathVariable(name = "id") final Long id) {
//        payService.delete(id);
//        return ResponseEntity.noContent().build();
//    }


