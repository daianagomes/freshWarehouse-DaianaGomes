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
    /** 40010 -  "SEDEX s/ contrato" **/
    private Integer shippingCode;

   /* public void shippingType(String shippingCode){
        switch (Integer.parseInt(shippingCode)) {
            case 40010:
                this.shippingType = "SEDEX s/ contrato";
                break;
            case 40045:
                this.shippingType =  "SEDEX a Cobrar, sem contrato";
                break;
            case 40126:
                this.shippingType =  "SEDEX a Cobrar, com contrato";
                break;
            case 40215:
                this.shippingType =  "SEDEX 10, sem contrato";
                break;
            case 40290:
                this.shippingType =  "SEDEX Hoje, sem contrato";
                break;
            case 40096:
                this.shippingType =  "SEDEX com contrato";
                break;
            case 40436:
                this.shippingType =  "SEDEX com contrato";
                break;
            case 40444:
                this.shippingType =  "SEDEX com contrato";
                break;
            case 40568:
                this.shippingType =  "SEDEX com contrato";
                break;
            case 40606:
                this.shippingType =  "SEDEX com contrato";
                break;
            case 41106:
                this.shippingType =  "PAC sem contrato";
                break;
            case 41068:
                this.shippingType =  "PAC com contrato";
                break;
            case 81019:
                this.shippingType =  "e-SEDEX, com contrato";
                break;
            case 81027:
                this.shippingType =  "e-SEDEX Prioritário, com conrato";
                break;
            case 81035:
                this.shippingType =  "e-SEDEX Express, com contrato";
                break;
            case 81868:
                this.shippingType =  "(Grupo 1) e-SEDEX, com contrato";
                break;
            case 81833:
                this.shippingType =  "(Grupo 2) e-SEDEX, com contrato";
                break;
            case 81850:
                this.shippingType =  "(Grupo 3) e-SEDEX, com contrato";
                break;
            default:
                throw new RuntimeException(String.format("Shippment code %s is not avaliable", shippingType));
        }*/
}
