package com.meli.freshWarehouse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.NOT_FOUND)

public class WarehouseNotFoundException extends NotFoundException {

    public WarehouseNotFoundException(String message) {
        super(message);
    }

}
