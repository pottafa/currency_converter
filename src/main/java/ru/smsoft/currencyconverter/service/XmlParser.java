package ru.smsoft.currencyconverter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ru.smsoft.currencyconverter.entity.Currency;
import ru.smsoft.currencyconverter.entity.ExchangeRate;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class XmlParser {
    private final Logger logger = LoggerFactory.getLogger(XmlParser.class);

    private final List<Currency> currencies = new ArrayList<>();

    @Value("${currency_source}")
    private String source;
    @Value("${element_name}")
    private String elementName;

    public List<Currency> parseCurrency(LocalDate date) {
        source = source.concat(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        return parseCurrency();
    }

    public List<Currency> parseCurrency() {
        Document document = readDocument();
        collectInformation(document, elementName);
        return currencies;
    }

    private Document readDocument() {
        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(source);
            doc.getDocumentElement().normalize();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return doc;
    }

    private void collectInformation(Document doc, final String element) {
        if (element.equals("Valute")) {
            try {
                NodeList nodeList = doc.getElementsByTagName(element);
                String dateAttribute = doc.getDocumentElement().getAttribute("Date");
                LocalDate rateDate = LocalDate.parse(dateAttribute, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                initBaseCurrency(rateDate);
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element elem = (Element) node;
                        Currency currency = new Currency(
                                elem.getElementsByTagName("CharCode").item(0).getTextContent(),
                                elem.getAttribute("ID"),
                                elem.getElementsByTagName("NumCode").item(0).getTextContent(),
                                elem.getElementsByTagName("Name").item(0).getTextContent()
                        );
                        ExchangeRate exchangeRate = new ExchangeRate(
                                rateDate,
                                new BigDecimal(elem.getElementsByTagName("Value").item(0).getTextContent().replaceAll(",", ".")),
                                Integer.parseInt(elem.getElementsByTagName("Nominal").item(0).getTextContent())
                        );
                        currency.setExchangeRate(exchangeRate);
                        currencies.add(currency);
                    }
                }
            } catch (Exception ex) {
                logger.error(ex.getMessage());
            }
        }
    }

    private void initBaseCurrency(LocalDate date) {
        Currency basicCurrency = new Currency("RUB", "", "643", "Российский рубль");
        ExchangeRate basicRate = new ExchangeRate(date, new BigDecimal(1), 1);
        basicCurrency.setExchangeRate(basicRate);
        currencies.add(basicCurrency);
    }
}
