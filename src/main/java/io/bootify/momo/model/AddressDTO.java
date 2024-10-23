package io.bootify.momo.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AddressDTO {

    private Long id;

    @NotNull
    private Integer zonecode;

    @NotNull
    @Size(max = 100)
    private String addresss;

    @Size(max = 50)
    private String addressDetail;

    @NotNull
    @Size(max = 20)
    private String addressName;

    @NotNull
    @Size(max = 11)
    private String addressContact;

    @NotNull
    private Long member;

}
