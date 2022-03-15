package com.example.oril_test_task;

import com.example.oril_test_task.persistence.entity.Currency;
import com.example.oril_test_task.persistence.entity.Price;
import com.example.oril_test_task.persistence.repository.CurrencyRepository;
import com.example.oril_test_task.persistence.repository.PriceRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.List;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class})
public class OrilTestTaskApplication {

    private final CurrencyRepository currencyRepository;

    public OrilTestTaskApplication(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(OrilTestTaskApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        List<Currency> currencies = currencyRepository.findAll();
        if (currencies.size() == 0) {
            Currency currency = new Currency();
            currency.setCode("BTC");
            currencyRepository.save(currency);

            currency = new Currency();
            currency.setCode("ETH");
            currencyRepository.save(currency);

            currency = new Currency();
            currency.setCode("XRP");
            currencyRepository.save(currency);
        }
    }
}
