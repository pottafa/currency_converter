package ru.smsoft.currencyconverter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.smsoft.currencyconverter.controller.message.ApiError;
import ru.smsoft.currencyconverter.entity.Operation;
import ru.smsoft.currencyconverter.service.ConverterService;
import ru.smsoft.currencyconverter.dto.CurrencySelectView;
import ru.smsoft.currencyconverter.service.OperationService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
public class ConverterRestController {
    @Autowired
    private ConverterService converterService;
    @Autowired
    private OperationService operationService;

    @GetMapping("/api/from/{from}/to/{to}/{quantity}")
    public ResponseEntity<?> convertCurrencies(@PathVariable("from") String from, @PathVariable("to") String to, @PathVariable("quantity") BigDecimal quantity) {
        LocalDate currentDate = LocalDate.now();
        BigDecimal result = converterService.convert(from,to,quantity, currentDate);
        if(result == null) {
            return ResponseEntity.ok(new ApiError("Conversion error"));
        }
        Operation operation = new Operation(from, to, quantity, result, currentDate);
        operationService.createOperation(operation);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/api/currencies")
    public ResponseEntity<?> getCurrenciesList() {
        List<CurrencySelectView> currencyList = converterService.getAllCurrencies();
        return ResponseEntity.ok(currencyList);
    }

    @GetMapping("/api/operations/from/{from}/to/{to}/{date}")
    public ResponseEntity<?> getOperationsList(@PathVariable("from") String from, @PathVariable("to") String to, @PathVariable("date") String date) {
        LocalDate searchDate = LocalDate.parse(date);
        List<Operation> operationList = operationService.getOperations(from, to, searchDate);
        return ResponseEntity.ok(operationList);
    }

}
