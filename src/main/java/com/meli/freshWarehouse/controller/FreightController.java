package com.meli.freshWarehouse.controller;

import com.meli.freshWarehouse.exception.NotFoundException;
import com.meli.freshWarehouse.model.Buyer;
import com.meli.freshWarehouse.model.Freight;
import com.meli.freshWarehouse.service.BuyerService;
import com.meli.freshWarehouse.service.FreightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/fresh-products/freight")
public class FreightController {

    @Autowired
    private FreightService freightService;

    @Autowired
    private BuyerService buyerService;

    /**
     * Get the price and delivery forecast
     *
     * @param buyer.id - Buyer id.
     * @return A buyer and delivery Date return an exception if buyer and shipping Code isn't found.
     * @throws NotFoundException When a buyer doesn't exist.
     * @see <http://localhost:8080/api/v1/fresh-products/freight/calculate?buyer.id={buyerId}&shipping.code={shippingCode}</a>
     */
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
