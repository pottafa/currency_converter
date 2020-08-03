package ru.smsoft.currencyconverter.repository;

import org.springframework.data.repository.CrudRepository;
import ru.smsoft.currencyconverter.entity.Operation;

import java.time.LocalDate;
import java.util.List;

public interface OperationRepository extends CrudRepository<Operation, Long> {
    List<Operation> findAllByOriginalCurrencyAndTargetCurrencyAndDate(String originalCurrency, String targetCurrency, LocalDate date);
}
