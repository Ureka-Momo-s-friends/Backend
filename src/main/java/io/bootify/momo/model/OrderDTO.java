package io.bootify.momo.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderDTO {

    private Long id;

    @NotNull
    private OffsetDateTime orderTime;

    @NotNull
    @Size(max = 10)
    private String status;

    @NotNull
    private Long member;

    @NotNull
    private Long address;

}
