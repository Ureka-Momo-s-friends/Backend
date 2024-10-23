package io.bootify.momo.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PetDTO {

    private Long id;

    @NotNull
    @Size(max = 15)
    private String petName;

    private LocalDate birthDate;

    @Size(max = 500)
    private String profileImgUrl;

    @NotNull
    private Boolean gender;

    @NotNull
    private Long member;

}
