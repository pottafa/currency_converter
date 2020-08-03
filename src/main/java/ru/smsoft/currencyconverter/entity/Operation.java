package ru.smsoft.currencyconverter.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "operations")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(name = "original_currency")
    private String originalCurrency;
    @Column(name = "target_currency")
    private String targetCurrency;
    @Column(name = "original_amount")
    private BigDecimal originalAmount;
    @Column(name = "received_amount")
    private BigDecimal receivedAmount;
    @Column(name = "date")
    private LocalDate date;

    public Operation() {
    }

    public Operation(String originalCurrency, String targetCurrency, BigDecimal originalAmount, BigDecimal receivedAmount, LocalDate date) {
        this.originalCurrency = originalCurrency;
        this.targetCurrency = targetCurrency;
        this.originalAmount = originalAmount;
        this.receivedAmount = receivedAmount;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOriginalCurrency() {
        return originalCurrency;
    }

    public void setOriginalCurrency(String originalCurrency) {
        this.originalCurrency = originalCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public BigDecimal getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(BigDecimal receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
