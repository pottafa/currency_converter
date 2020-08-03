package ru.smsoft.currencyconverter.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "exchange_rates")
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "value")
    private BigDecimal value;
    @Column(name = "nominal")
    private int nominal;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "currency_id")
    private Currency currency;

    public ExchangeRate() {
    }

    public ExchangeRate(LocalDate date, BigDecimal value, int nominal) {
        this.date = date;
        this.value = value;
        this.nominal = nominal;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "date=" + date +
                ", value=" + value +
                ", nominal=" + nominal +
                '}';
    }
}
