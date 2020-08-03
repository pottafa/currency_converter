package ru.smsoft.currencyconverter.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.smsoft.currencyconverter.dto.CurrencySelectView;
import ru.smsoft.currencyconverter.entity.Currency;

import java.util.List;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, String> {
    List<CurrencySelectView> findAllCurrencySelectViewBy();
}
