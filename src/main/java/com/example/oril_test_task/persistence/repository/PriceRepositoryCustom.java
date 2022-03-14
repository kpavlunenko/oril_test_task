package com.example.oril_test_task.persistence.repository;

import com.example.oril_test_task.persistence.entity.Price;

import java.util.List;

public interface PriceRepositoryCustom {
    Price findMinPriceByCurrencyId(Long id);
    Price findMaxPriceByCurrencyId(Long id);
    List<Price> findAll(Long id, Integer page, Integer size);
}
