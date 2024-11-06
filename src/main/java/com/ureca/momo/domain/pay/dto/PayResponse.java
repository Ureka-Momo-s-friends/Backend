package com.ureca.momo.domain.pay.dto;

import com.ureca.momo.domain.order.model.Orders;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PayResponse {
    private Long id;                     // 결제 ID
    private Integer amount;              // 결제 금액
    private String status;               // 결제 상태
    private String paymentKey;           // 결제 키
    private LocalDateTime paymentDate;   // 결제 일시
    private Orders orders;               // 주문 정보 포함

    // 생성자
    public PayResponse(Long id, Integer amount, String status, String paymentKey, Orders orders, LocalDateTime paymentDate) {
        this.id = id;
        this.amount = amount;
        this.status = status;
        this.paymentKey = paymentKey;
        this.orders = orders;
        this.paymentDate = paymentDate;
    }

    // 추가로 직렬화에 필요한 주문 정보 필드를 직접 DTO로 만들 수도 있음
    public String getOrderName() {
        return orders != null ? orders.getOrderName() : "상품 정보 없음";
    }
}
