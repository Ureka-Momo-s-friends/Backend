package io.bootify.momo.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CartDTO {

    private Long id;

    @NotNull
    private Integer amount;

    @NotNull
    private Long member;

    @NotNull
    private Long product;

}
