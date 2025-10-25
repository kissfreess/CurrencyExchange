package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@AllArgsConstructor
public class ExchangeRates {

    private Long id;
    private Currencies baseCurrency;
    private Currencies targetCurrency;
    private double rate;

    public ExchangeRates() {
    }
}
