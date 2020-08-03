package ru.smsoft.currencyconverter.entity;

import javax.persistence.*;

@Entity
@Table(name = "currencies")
public class Currency {
    @Id
    private String id;
    @Column(name = "valute_id")
    private String valuteId;
    @Column(name = "num_code")
    private String numCode;
    @Column(name = "name")
    private String name;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "currency", cascade = CascadeType.ALL, orphanRemoval = true)
    private ExchangeRate exchangeRate;

    public Currency() {}

    public Currency(String charCode, String valuteId, String numCode, String name) {
        this.id = charCode;
        this.valuteId = valuteId;
        this.numCode = numCode;
        this.name = name;
    }

    public ExchangeRate getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(ExchangeRate exchangeRate) {
        if (exchangeRate == null) {
            if (this.exchangeRate != null) {
                this.exchangeRate.setCurrency(null);
            }
        } else {
            exchangeRate.setCurrency(this);
        }
        this.exchangeRate = exchangeRate;
    }

    public String getValuteId() {
        return valuteId;
    }

    public void setValuteId(String valuteId) {
        this.valuteId = valuteId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumCode() {
        return numCode;
    }

    public void setNumCode(String numCode) {
        this.numCode = numCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id='" + id + '\'' +
                ", valuteId='" + valuteId + '\'' +
                ", numCode=" + numCode +
                ", name='" + name + '\'' +
                ", exchangeRate=" + exchangeRate +
                '}';
    }
}
