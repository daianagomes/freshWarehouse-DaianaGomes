package com.meli.freshWarehouse.service;

import com.meli.freshWarehouse.model.Buyer;
import com.meli.freshWarehouse.model.Freight;
import com.meli.freshWarehouse.model.PurchaseOrder;
import com.meli.freshWarehouse.model.ShoppingCartProduct;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class FreightService {

    public Freight calculateFreight(Buyer buyer, Integer shippingCode) {
         Optional<Set<ShoppingCartProduct>> shoppingCartProduct = buyer.getPurchaseOrders().stream()
                 .map(PurchaseOrder::getShoppingCartProducts)
                 .findAny();

            if (shoppingCartProduct.isPresent()) {
                Freight freight = Freight.builder().buyer(buyer).cartProducts(shoppingCartProduct.get()).shippingCode(shippingCode).build();
                return ExternalAPIHelper.calculateFreight(freight);
            }

        return null;
    }
}
