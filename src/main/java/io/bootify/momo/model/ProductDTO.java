package io.bootify.momo.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductDTO {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    private Integer price;

    @NotNull
    private Integer salePrice;

    @Size(max = 500)
    private String thumbnail;

    @Size(max = 500)
    private String detail;

    @NotNull
    private Long category;

}
