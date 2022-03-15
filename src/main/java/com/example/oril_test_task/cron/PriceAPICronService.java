package com.example.oril_test_task.cron;

import com.example.oril_test_task.service.PriceAPIService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PriceAPICronService {

    private final PriceAPIService priceAPIService;

    public PriceAPICronService(PriceAPIService priceAPIService) {
        this.priceAPIService = priceAPIService;
    }

    @Scheduled(fixedRate = 1000 * 30)
    public void syncPriceToAPI() {
        priceAPIService.syncPriceToAPI();
    }
}
