package com.meli.freshWarehouse.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
@Getter @Setter
public class BuyerDto {

    @NotEmpty(message = "Buyer name cannot be empty.")
    @Size(max = 50, message = "Buyer name can't exceed 50 characters.")
    @Pattern(regexp = "^[A-Z][a-z]*(?: [A-Z][a-z]*)*$",
            message = "The representative's name must begin with a capital letter.")
    private String name;
    private String zipCode;
}
