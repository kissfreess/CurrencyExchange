package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class ExchangeRates {

    private Long id;
    private Currencies baseCurrency;
    private Currencies targetCurrency;
    private double rate;

    public ExchangeRates() {
    }
}
