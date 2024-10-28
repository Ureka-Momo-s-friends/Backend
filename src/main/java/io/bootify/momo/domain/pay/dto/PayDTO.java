package io.bootify.momo.domain.pay.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
@Setter
public class PayDTO {

    private Long id; // Payment ID

    @NotNull
    private Integer amount; // Payment amount

    @NotNull
    @Size(max = 10)
    private String status; // Payment status (e.g., completed, pending, etc.)

    @NotNull
    @Size(max = 200)
    private String paymentKey; // Unique key for the payment

    private Long orderId; // Associated order ID (to maintain only necessary reference)
}
