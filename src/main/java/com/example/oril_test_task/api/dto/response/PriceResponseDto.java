package com.example.oril_test_task.api.dto.response;

import com.example.oril_test_task.persistence.entity.Price;

public class PriceResponseDto {

    private String name;
    private String price;

    public PriceResponseDto() {
    }

    public PriceResponseDto(Price price) {
        this.name = price.getCurrency().getCode();
        this.price = price.getPrice().toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
