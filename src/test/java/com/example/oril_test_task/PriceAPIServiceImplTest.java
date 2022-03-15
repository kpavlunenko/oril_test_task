package com.example.oril_test_task;

import com.example.oril_test_task.cron.model.PriceCexIoModel;
import com.example.oril_test_task.persistence.entity.Currency;
import com.example.oril_test_task.persistence.repository.CurrencyRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class PriceAPIServiceImplTest {

    @Value("${defaultCurrency}")
    private String defaultCurrency;

    @Value("${priceApi.url}")
    private String url;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Test
    void shouldBeReceivedResponseWithCorrectCurrencies() {
        Currency currency = currencyRepository.findAll().stream().findFirst().get();
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

        Assertions.assertTrue(response.getStatusCodeValue() == 200);
        PriceCexIoModel priceCexIoModel = response.getBody();
        Assertions.assertTrue(priceCexIoModel.getLprice() != null);
        Assertions.assertEquals(priceCexIoModel.getCurr1(), currency.getCode());
        Assertions.assertEquals(priceCexIoModel.getCurr2(), defaultCurrency);
    }
}
