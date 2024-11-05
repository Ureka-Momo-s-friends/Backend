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
        Pay savedPay = payService.savePayment(pay); // 서비스의 savePayment 메서드 사용
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

    // 모든 결제 내역 가져오기
    @GetMapping("/all")
    public ResponseEntity<List<Pay>> getAllPayments() {
        List<Pay> payments = payService.getAllPayments();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    // 결제 데이터 저장 엔드포인트
    @PostMapping("/save")
    public ResponseEntity<String> savePayment(@RequestBody Pay pay) {
        try {
            payService.savePayment(pay);
            return ResponseEntity.ok("결제 정보가 저장되었습니다.");
        } catch (Exception e) {
            e.printStackTrace(); // 구체적인 오류 로그 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("결제 정보 저장 중 오류 발생");
        }
    }

    // 결제 취소 엔드포인트
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
            try {
                payRepository.delete(payOptional.get());
                return ResponseEntity.ok("결제 정보가 성공적으로 삭제되었습니다.");
            } catch (Exception e) {
                // 삭제 시 예외가 발생했을 경우
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("결제 정보를 삭제하는 중 오류가 발생했습니다.");
            }
        } else {
            // 해당 paymentKey에 대한 정보가 없을 경우
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 결제 키에 대한 결제 정보가 없습니다.");
        }
    }

}
