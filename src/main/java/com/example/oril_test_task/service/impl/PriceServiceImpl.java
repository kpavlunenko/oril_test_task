package com.example.oril_test_task.service.impl;

import au.com.bytecode.opencsv.CSVWriter;
import com.example.oril_test_task.exception.DataNotFound;
import com.example.oril_test_task.persistence.entity.Currency;
import com.example.oril_test_task.persistence.entity.Price;
import com.example.oril_test_task.persistence.repository.CurrencyRepository;
import com.example.oril_test_task.persistence.repository.PriceRepositoryCustom;
import com.example.oril_test_task.service.PriceService;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PriceServiceImpl implements PriceService {

    private final PriceRepositoryCustom priceRepositoryCustom;
    private final CurrencyRepository currencyRepository;

    public PriceServiceImpl(PriceRepositoryCustom priceRepositoryCustom, CurrencyRepository currencyRepository) {
        this.priceRepositoryCustom = priceRepositoryCustom;
        this.currencyRepository = currencyRepository;
    }

    @Override
    public Price minPrice(String code) {
        Optional<Currency> currency = currencyRepository.findCurrencyByCode(code);
        if (!currency.isPresent()) {
            throw new DataNotFound("currency not found");
        }
        Price price = priceRepositoryCustom.findMinPriceByCurrencyId(currency.get().getId());
        if (price == null) {
            throw new DataNotFound("price not found");
        }
        return price;
    }

    @Override
    public Price maxPrice(String code) {
        Optional<Currency> currency = currencyRepository.findCurrencyByCode(code);
        if (!currency.isPresent()) {
            throw new DataNotFound("currency not found");
        }
        Price price = priceRepositoryCustom.findMaxPriceByCurrencyId(currency.get().getId());
        if (price == null) {
            throw new DataNotFound("price not found");
        }
        return price;
    }

    @Override
    public List<Price> findAll(String code, Integer page, Integer size) {
        Optional<Currency> currency = currencyRepository.findCurrencyByCode(code);
        if (!currency.isPresent()) {
            throw new DataNotFound("currency not found");
        }
        return priceRepositoryCustom.findAll(currency.get().getId(), page, size);
    }

    @Override
    public String getCSVFile() {
        String filePath = "./files";
        String fileName = "prices.csv";
        List<String[]> csvFile = new ArrayList<>();
        String[] header = new String[3];
        header[0] = "Cryptocurrency Name";
        header[1] = "Min Price";
        header[2] = "Max Price";
        csvFile.add(header);

        List<Currency> currencies = currencyRepository.findAll();
        currencies.forEach(currency -> csvFile.add(new String[]{
                currency.getCode(),
                minPrice(currency.getCode()).getPrice().toString(),
                maxPrice(currency.getCode()).getPrice().toString()}));

        try {
            if (Files.notExists(Path.of(filePath))) {
                Files.createDirectories(Path.of(filePath));
            }
            String allPath = filePath + "\\" + fileName;
            if (Files.notExists(Path.of(allPath))) {
                Files.createFile(Path.of(allPath));
            }
        } catch (IOException e) {
            throw new DataNotFound("Cannot find or create file" + fileName);
        }

        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath + "\\" + fileName))) {
            writer.writeAll(csvFile);
        } catch (IOException e) {
            throw new DataNotFound("Cannot write file" + fileName);
        }
        return fileName;
    }
}
