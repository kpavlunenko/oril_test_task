package com.example.oril_test_task.service.impl;

import com.example.oril_test_task.cron.model.PriceCexIoModel;
import com.example.oril_test_task.persistence.entity.Currency;
import com.example.oril_test_task.persistence.entity.Price;
import com.example.oril_test_task.persistence.repository.CurrencyRepository;
import com.example.oril_test_task.persistence.repository.PriceRepository;
import com.example.oril_test_task.service.PriceAPIService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class PriceAPIServiceImpl implements PriceAPIService {

    @Value("${defaultCurrency}")
    private String defaultCurrency;

    @Value("${priceApi.url}")
    private String url;

    private final PriceRepository priceRepository;
    private final CurrencyRepository currencyRepository;

    public PriceAPIServiceImpl(PriceRepository priceRepository, CurrencyRepository currencyRepository) {
        this.priceRepository = priceRepository;
        this.currencyRepository = currencyRepository;
    }

    @Override
    public void syncPriceToAPI() {

        List<Currency> currencies = currencyRepository.findAll();
        for (Currency currency : currencies) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
            MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
            converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
            messageConverters.add(converter);
            restTemplate.setMessageConverters(messageConverters);
            HttpEntity httpEntity = new HttpEntity(headers);
            ResponseEntity<PriceCexIoModel> response = restTemplate.exchange(
                    url + currency.getCode() + "/" + defaultCurrency,
                    HttpMethod.GET,
                    httpEntity,
                    PriceCexIoModel.class
            );

            if (response.getStatusCodeValue() == 200) {
                PriceCexIoModel priceCexIoModel = response.getBody();
                if (priceCexIoModel.getLprice() != null) {
                    Price price = new Price();
                    price.setCurrency(currency);
                    price.setCreated(new Date());
                    price.setPrice(Double.parseDouble(priceCexIoModel.getLprice()));
                    priceRepository.save(price);
                }
            }
        }
    }
}
