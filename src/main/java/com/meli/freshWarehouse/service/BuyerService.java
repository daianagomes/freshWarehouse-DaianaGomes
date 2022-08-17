package com.meli.freshWarehouse.service;

import com.meli.freshWarehouse.dto.BuyerDto;
import com.meli.freshWarehouse.exception.NotFoundException;
import com.meli.freshWarehouse.model.Buyer;
import com.meli.freshWarehouse.repository.IBuyerRepo;
import org.springframework.stereotype.Service;

@Service
public class BuyerService implements IBuyerService {

    private final IBuyerRepo repository;

    public BuyerService(IBuyerRepo repository) {
        this.repository = repository;
    }

    @Override
    public Buyer getBuyerById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Buyer not found!"));
    }

    @Override
    public Buyer insertBuyer(BuyerDto buyerDto) {

        return repository.save(Buyer.builder()
                .name(buyerDto.getName())
                .zipCode(buyerDto.getZipCode())
                .build());
    }

    @Override
    public void deleteBuyer(Long id) {
        getBuyerById(id);

        repository.deleteById(id);
    }

    @Override
    public Buyer updateBuyer(Long id, BuyerDto buyerDto) {

        Buyer buyerFound = getBuyerById(id);
        buyerFound.setName(buyerDto.getName());
        return repository.save(buyerFound);
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
