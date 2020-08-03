package ru.smsoft.currencyconverter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConverterController {

    private static final String CONVERTER_PAGE_TEMPLATE = "currency-converter";

    @GetMapping("/currency-converter")
    protected String converterView(Model model) {
        return CONVERTER_PAGE_TEMPLATE;
    }

}
