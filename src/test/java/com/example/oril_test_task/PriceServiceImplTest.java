package com.example.oril_test_task;

import com.example.oril_test_task.exception.DataNotFound;
import com.example.oril_test_task.persistence.entity.Currency;
import com.example.oril_test_task.persistence.entity.Price;
import com.example.oril_test_task.persistence.repository.CurrencyRepository;
import com.example.oril_test_task.persistence.repository.PriceRepository;
import com.example.oril_test_task.service.PriceService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class PriceServiceImplTest {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private PriceService priceService;

    @BeforeAll
    void init() {
        priceRepository.deleteAll();
        Currency currency = currencyRepository.findAll().stream().filter(currencyFilter -> currencyFilter.getCode().equals("BTC")).findFirst().get();
        Price price = new Price();
        price.setCurrency(currency);
        price.setCreated(new Date());
        price.setPrice(20.0);
        priceRepository.save(price);

        price = new Price();
        price.setCurrency(currency);
        price.setCreated(new Date());
        price.setPrice(999999.0);
        priceRepository.save(price);

        currency = currencyRepository.findAll().stream().filter(currencyFilter -> currencyFilter.getCode().equals("ETH")).findFirst().get();
        price = new Price();
        price.setCurrency(currency);
        price.setCreated(new Date());
        price.setPrice(10.0);
        priceRepository.save(price);

        price = new Price();
        price.setCurrency(currency);
        price.setCreated(new Date());
        price.setPrice(1999999.0);
        priceRepository.save(price);
    }

    @Test
    void shouldBeReturnMinPriceWhenInDBManyPrices(){
        Price price = priceService.minPrice("BTC");
        Assertions.assertEquals(20.0, price.getPrice());
    }

    @Test
    void shouldBeReturnMaxPriceWhenInDBManyPrices(){
        Price price = priceService.maxPrice("BTC");
        Assertions.assertEquals(999999.0, price.getPrice());
    }

    @Test
    void shouldBeReturnOneRecordWhenSizeIsOne(){
        List<Price> prices = priceService.findAll("BTC", 0, 1);
        Assertions.assertTrue(prices.size() == 1);
    }

    @Test
    void shouldBeReturnExceptionWhenSizeIsZero(){
        Exception exception = assertThrows(DataNotFound.class, () -> {
            List<Price> prices = priceService.findAll("BTC", 0, 0);
        });
    }

    @Test
    void shouldBeReturnExceptionWhenCurrencyNotFound(){
        Exception exception = assertThrows(DataNotFound.class, () -> {
            List<Price> prices = priceService.findAll("TEST", 0, 0);
        });
    }

    @Test
    void shouldBeSortedByPriceWhenFindAll(){
        List<Price> prices = priceService.findAll("BTC", 0, 10);
        Assertions.assertEquals(20.0, prices.stream().findFirst().get().getPrice());
        Assertions.assertEquals(999999.0, prices.get(prices.size() - 1).getPrice());
    }

}
