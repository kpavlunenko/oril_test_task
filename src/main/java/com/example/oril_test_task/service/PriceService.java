package com.example.oril_test_task.service;

import com.example.oril_test_task.persistence.entity.Price;

import java.util.List;

public interface PriceService {

    Price minPrice(String name);

    Price maxPrice(String name);

    List<Price> findAll(String name, Integer page, Integer size);

    String getCSVFile();
}
