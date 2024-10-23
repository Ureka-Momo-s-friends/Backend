package io.bootify.momo.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderDetailDTO {

    private Long id;

    @NotNull
    private Integer amount;

    @NotNull
    private Long order;

    @NotNull
    private Long prodcut;

}
