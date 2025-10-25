package service;

import model.ExchangeRates;
import repository.CurrenciesRepository;
import repository.ExchangeRatesRepository;

import java.util.Optional;

public class ExchangeService {

    private final CurrenciesRepository currenciesRepository = new CurrenciesRepository();
    private final ExchangeRatesRepository exchangeRatesRepository = new ExchangeRatesRepository();

    public double getExchange(String baseCurrencyCode, String targetCurrencyCode, double amount) {

        ExchangeRates exchangeRate = null;

        Optional<ExchangeRates> maybeExcangeRate = exchangeRatesRepository.findByCode(baseCurrencyCode, targetCurrencyCode);
        if (maybeExcangeRate.isPresent()) {
            exchangeRate = maybeExcangeRate.get();
        }

        double rate = exchangeRate.getRate();

        return amount * rate;
    }
}
