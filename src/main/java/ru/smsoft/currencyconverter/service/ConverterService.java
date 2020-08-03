package ru.smsoft.currencyconverter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smsoft.currencyconverter.dto.CurrencySelectView;
import ru.smsoft.currencyconverter.entity.ExchangeRate;
import ru.smsoft.currencyconverter.repository.CurrencyRepository;
import ru.smsoft.currencyconverter.repository.ExchangeRateRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
public class ConverterService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConverterService.class);

    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;
    @Autowired
    private XmlParser xmlParser;

    @Transactional
    public BigDecimal convert(String from, String to, BigDecimal quantity, LocalDate date) {
        if (date.getDayOfWeek() == DayOfWeek.SUNDAY || date.getDayOfWeek() == DayOfWeek.MONDAY) {
            do {
                date = date.minusDays(1);
            } while (date.getDayOfWeek() != DayOfWeek.SATURDAY);
        }
        ExchangeRate exchangeRateFrom = findRate(from, date);
        ExchangeRate exchangeRateTo = findRate(to, date);
        if (exchangeRateFrom == null || exchangeRateTo == null) {
            LOGGER.error("Exchange rates for currencies {}, {} with the date {} were not found.", from, to, date);
            return null;
        }

        long nominalFrom = exchangeRateFrom.getNominal();
        BigDecimal valueFromByNominal = exchangeRateFrom.getValue().divide(new BigDecimal(nominalFrom), 4, RoundingMode.HALF_UP);

        long nominalTo = exchangeRateTo.getNominal();
        BigDecimal valueToByNominal = exchangeRateTo.getValue().divide(new BigDecimal(nominalTo), 4, RoundingMode.HALF_UP);

        return valueFromByNominal.divide(valueToByNominal, 4, RoundingMode.HALF_UP).multiply(quantity);
    }

    public List<CurrencySelectView> getAllCurrencies() {
        return currencyRepository.findAllCurrencySelectViewBy();
    }

    private ExchangeRate findRate(String from, LocalDate date) {
        ExchangeRate exchangeRate = exchangeRateRepository.findByCurrency_IdAndDate(from, date);
        if (exchangeRate == null) {
            LOGGER.info("Exchange rate for currency {} with the date {} was not found. The check for availability of updates starts", from, date);
            xmlParser.parseCurrency(date);
            exchangeRate = exchangeRateRepository.findByCurrency_IdAndDate(from, date);
        }
        return exchangeRate;
    }


}
