package io.bootify.momo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class StrayCatDTO {

    private Long id;

    @NotNull
    @Size(max = 500)
    private String catImgUrl;

    @NotNull
    @Digits(integer = 18, fraction = 8)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal lat;

    @NotNull
    @Digits(integer = 19, fraction = 8)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal lon;

    @NotNull
    private Long member;

}
