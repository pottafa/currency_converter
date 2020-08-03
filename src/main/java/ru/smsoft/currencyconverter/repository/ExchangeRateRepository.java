package ru.smsoft.currencyconverter.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.smsoft.currencyconverter.entity.ExchangeRate;

import java.time.LocalDate;

@Repository
public interface ExchangeRateRepository extends CrudRepository<ExchangeRate, String> {
    ExchangeRate findByCurrency_IdAndDate(String id, LocalDate date);
}
