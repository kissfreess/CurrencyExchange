package service;

import exception.CurrencyNotFoundException;
import exception.ExchangeRateNotFoundException;
import model.ExchangeRates;
import repository.CurrenciesRepository;
import repository.ExchangeRatesRepository;

import java.util.Optional;

public class ExchangeService {

    private final CurrenciesRepository currenciesRepository = new CurrenciesRepository();
    private final ExchangeRatesRepository exchangeRatesRepository = new ExchangeRatesRepository();

    public double getExchange(String baseCurrencyCode, String targetCurrencyCode, double amount) {

        Optional<ExchangeRates> maybeExcangeRate = exchangeRatesRepository.findByCode(baseCurrencyCode, targetCurrencyCode);
        if (maybeExcangeRate.isEmpty()) {
            throw new ExchangeRateNotFoundException("Exchange rate not found for: " + baseCurrencyCode + " to " + targetCurrencyCode);
        }

        ExchangeRates exchangeRate = maybeExcangeRate.get();
        double rate = exchangeRate.getRate();

        return amount * rate;
    }
}
