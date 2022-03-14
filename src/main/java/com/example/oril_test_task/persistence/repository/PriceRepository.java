package com.example.oril_test_task.persistence.repository;

import com.example.oril_test_task.persistence.entity.Price;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PriceRepository extends MongoRepository<Price, Long> {
}
