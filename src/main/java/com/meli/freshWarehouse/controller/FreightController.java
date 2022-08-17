package com.meli.freshWarehouse.controller;

import com.meli.freshWarehouse.model.Buyer;
import com.meli.freshWarehouse.model.Freight;
import com.meli.freshWarehouse.model.PurchaseOrder;
import com.meli.freshWarehouse.model.ShoppingCartProduct;
import com.meli.freshWarehouse.service.BuyerService;
import com.meli.freshWarehouse.service.FreightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/v1/fresh-products/freight")
public class FreightController {

    @Autowired
    private FreightService freightService;

    @Autowired
    private BuyerService buyerService;

    @GetMapping("/calculate")
    public ResponseEntity<Freight> calculateFreight(@RequestParam(name = "buyer.id") Long buyerId, @RequestParam(name = "shipping.code") Integer shippingCode){
        Optional<Buyer> optionalBuyer = Optional.ofNullable(buyerService.getBuyerById(buyerId));

        if(optionalBuyer.isPresent() && Objects.nonNull(shippingCode)) {
            Freight freight = freightService.calculateFreight(optionalBuyer.get(), shippingCode);
            if (Objects.nonNull(freight))
                return new ResponseEntity<>(freight, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
