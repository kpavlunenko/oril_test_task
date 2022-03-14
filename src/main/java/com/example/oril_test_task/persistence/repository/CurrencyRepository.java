package com.example.oril_test_task.persistence.repository;

import com.example.oril_test_task.persistence.entity.Currency;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CurrencyRepository extends MongoRepository<Currency, Long> {

    Optional<Currency> findCurrencyByCode(String code);
}
