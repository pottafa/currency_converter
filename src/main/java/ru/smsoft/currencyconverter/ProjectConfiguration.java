package ru.smsoft.currencyconverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import ru.smsoft.currencyconverter.entity.Currency;
import ru.smsoft.currencyconverter.repository.CurrencyRepository;
import ru.smsoft.currencyconverter.service.XmlParser;

import java.util.List;

@Configuration
public class ProjectConfiguration {
    @Autowired
    private CurrencyRepository repository;
    @Autowired
    private XmlParser xmlParser;

    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        List<Currency> currencyList = xmlParser.parseCurrency();
        repository.saveAll(currencyList);
    }
}