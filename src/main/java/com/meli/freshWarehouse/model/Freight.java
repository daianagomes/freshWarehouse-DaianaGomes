package com.meli.freshWarehouse.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Freight {
    @JsonIgnoreProperties
    private Buyer buyer;
    @JsonIgnoreProperties
    private Set<ShoppingCartProduct> cartProducts;
    private LocalDate deliveryDate;
    private double total;
    private Integer shippingCode;

}
