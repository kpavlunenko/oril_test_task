package com.example.oril_test_task.api.controller;

import com.example.oril_test_task.api.dto.response.PriceResponseDto;
import com.example.oril_test_task.facade.PriceFacade;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/cryptocurrencies")
public class CurrencyRateController {

    private final PriceFacade priceFacade;

    public CurrencyRateController(PriceFacade priceFacade) {
        this.priceFacade = priceFacade;
    }

    @GetMapping("")
    public ResponseEntity<List<PriceResponseDto>> findAll(@RequestParam String name, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(priceFacade.findAll(name, page, size));
    }

    @GetMapping("/minprice")
    public ResponseEntity<PriceResponseDto> minPrice(@RequestParam String name) {
        return ResponseEntity.ok(priceFacade.minPrice(name));
    }

    @GetMapping("/maxprice")
    public ResponseEntity<PriceResponseDto> maxPrice(@RequestParam String name) {
        return ResponseEntity.ok(priceFacade.maxPrice(name));
    }

    @GetMapping("/csv")
    public ResponseEntity getCSVFile() {
        String filename = priceFacade.getCSVFile();
        File file2Upload = new File("files/" + filename);
        Path path = Paths.get(file2Upload.getAbsolutePath());
        ByteArrayResource resource = null;
        try {
            resource = new ByteArrayResource(Files.readAllBytes(path));
        } catch (IOException e) {

        }
        return ResponseEntity.ok()
                .contentLength(file2Upload.length())
                .contentType(MediaType.parseMediaType("text/csv"))
                .header("Content-Disposition", "attachment; filename=" + filename)
                .body(resource);
    }

}
