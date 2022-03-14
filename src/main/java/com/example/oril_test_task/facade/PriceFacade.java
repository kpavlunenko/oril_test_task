package com.example.oril_test_task.facade;

import com.example.oril_test_task.api.dto.response.PriceResponseDto;

import java.util.List;

public interface PriceFacade {

    PriceResponseDto minPrice(String name);

    PriceResponseDto maxPrice(String name);

    List<PriceResponseDto> findAll(String name, Integer page, Integer size);

    String getCSVFile();
}
