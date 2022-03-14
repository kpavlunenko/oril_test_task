package com.example.oril_test_task.facade.impl;

import com.example.oril_test_task.api.dto.response.PriceResponseDto;
import com.example.oril_test_task.facade.PriceFacade;
import com.example.oril_test_task.persistence.entity.Price;
import com.example.oril_test_task.service.PriceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PriceFacadeImpl implements PriceFacade {

    private final PriceService priceService;

    public PriceFacadeImpl(PriceService priceService) {
        this.priceService = priceService;
    }

    @Override
    public PriceResponseDto minPrice(String name) {
        return new PriceResponseDto(priceService.minPrice(name));
    }

    @Override
    public PriceResponseDto maxPrice(String name) {
        return new PriceResponseDto(priceService.maxPrice(name));
    }

    @Override
    public List<PriceResponseDto> findAll(String name, Integer page, Integer size) {
        List<Price> all = priceService.findAll(name, page, size);
        List<PriceResponseDto> items = all.stream().map(PriceResponseDto::new).collect(Collectors.toList());
        return items;
    }

    @Override
    public String getCSVFile() {
        return priceService.getCSVFile();
    }
}
