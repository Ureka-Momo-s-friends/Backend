package io.bootify.momo.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PayDTO {

    private Long id;

    @NotNull
    private Integer amount;

    @NotNull
    @Size(max = 10)
    private String status;

    @NotNull
    @Size(max = 200)
    private String paymentKey;

    @NotNull
    private Long order;

}
