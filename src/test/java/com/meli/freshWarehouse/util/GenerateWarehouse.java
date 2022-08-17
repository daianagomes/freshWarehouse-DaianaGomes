package com.meli.freshWarehouse.util;

import com.meli.freshWarehouse.dto.WarehouseDTO;
import com.meli.freshWarehouse.dto.WarehouseForProductStockResponseDTO;
import com.meli.freshWarehouse.model.Warehouse;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

public class GenerateWarehouse {

    public static final Warehouse newWarehouse1() {
        return Warehouse.builder()
                .address("Rua um test")
                .city("São Paulo")
                .state("São Paulo")
                .country("Brasil")
                .number(23)
                .build();
    }

    public static final WarehouseDTO newWarehouseDto1() {
        WarehouseDTO warehouse = new WarehouseDTO("00000000", "Rua um test",
                "São Paulo",
                "São Paulo",
                "Brasil",
                23);

        return warehouse;

    }

    public static final WarehouseDTO newWarehouseDto2() {
        WarehouseDTO warehouse = new WarehouseDTO();
        warehouse.toModel().setAddress("Rua dois test");
        warehouse.toModel().setCity("Belo Horizonte");
        warehouse.toModel().setState("Minas Gerais");
        warehouse.toModel().setCountry("Brasil");
        warehouse.toModel().setNumber(25);

        return warehouse;

    }

    public static final Warehouse newWarehouse2() {
        return Warehouse.builder()
                .address("Rua dois test")
                .city("Belo Horizonte")
                .state("Minas Gerais")
                .country("Brasil")
                .number(25)
                .build();
    }

    public static final Warehouse updatedWarehouse() {
        return  Warehouse.builder()
                .id(1L)
                .address("Updated address")
                .city("Update city")
                .country("Updated country")
                .state("Updated state")
                .number(26)
                .build();
    }
    public static final Warehouse validWarehouse1() {

        return Warehouse.builder()
                .id(1L)
                .address("Rua um test")
                .city("São Paulo")
                .state("São Paulo")
                .country("Brasil")
                .number(23)
                .build();
    }

    public static final Warehouse validWarehouse2() {

        return Warehouse.builder()
                .id(2L)
                .address("Rua dois test")
                .city("Belo Horizonte")
                .state("Minas Gerais")
                .country("Brasil")
                .number(25)
                .build();
    }

    public static final List<String> expectedResultWarehouse() {
        List<String> expectedList =
                Arrays.asList("Rua um test", "São Paulo", "São Paulo", "Brasil", "23");

        return expectedList;
    }

    public static final List<WarehouseForProductStockResponseDTO> warehouseForProductStockResponseDTOWithStockAvailable() {
        return Arrays.asList(
                WarehouseForProductStockResponseDTO.builder()
                        .warehouseId(1L)
                        .totalQuantity(4L)
                        .build(),
                WarehouseForProductStockResponseDTO.builder()
                        .warehouseId(2L)
                        .totalQuantity(4L)
                        .build()
        );
    }

    public static final List<WarehouseForProductStockResponseDTO> warehouseForProductStockResponseDTOWithoutStockAvailable() {
        return Arrays.asList(
                WarehouseForProductStockResponseDTO.builder()
                        .warehouseId(1L)
                        .totalQuantity(0L)
                        .build(),
                WarehouseForProductStockResponseDTO.builder()
                        .warehouseId(2L)
                        .totalQuantity(0L)
                        .build()
        );
    }
}
