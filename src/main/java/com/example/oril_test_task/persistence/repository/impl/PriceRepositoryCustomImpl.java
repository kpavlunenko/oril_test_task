package com.example.oril_test_task.persistence.repository.impl;

import com.example.oril_test_task.exception.DataNotFound;
import com.example.oril_test_task.persistence.entity.Price;
import com.example.oril_test_task.persistence.repository.PriceRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PriceRepositoryCustomImpl implements PriceRepositoryCustom {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Price findMinPriceByCurrencyId(Long id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("currency.id").is(id));
        query.with(Sort.by(Sort.Direction.ASC, "price"));
        query.limit(1);
        Price price = mongoTemplate.findOne(query, Price.class);
        return price;
    }

    @Override
    public Price findMaxPriceByCurrencyId(Long id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("currency.id").is(id));
        query.with(Sort.by(Sort.Direction.DESC, "price"));
        query.limit(1);
        Price price = mongoTemplate.findOne(query, Price.class);
        return price;
    }

    @Override
    public List<Price> findAll(Long id, Integer page, Integer size) {
        Query query = new Query();
        query.addCriteria(Criteria.where("currency.id").is(id));
        if (size == 0) {
            throw new DataNotFound("size can not be 0");
        }
        Pageable pageableRequest = PageRequest.of(page, size);
        query.with(pageableRequest);
        query.with(Sort.by(Sort.Direction.ASC, "price"));
        List<Price> prices = mongoTemplate.find(query, Price.class);
        return prices;
    }
}
