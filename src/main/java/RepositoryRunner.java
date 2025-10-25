import model.ExchangeRates;
import repository.CurrenciesRepository;
import model.Currencies;
import repository.ExchangeRatesRepository;
import service.ExchangeService;

import java.util.List;
import java.util.Optional;

public class RepositoryRunner {
    public static void main(String[] args) {

        ExchangeService exchangeService = new ExchangeService();
        double sum = exchangeService.getExchange("USD", "BYN", 100);
        System.out.println(sum);


    }

    private static void findCurrencyByCode() {
        CurrenciesRepository currenciesRepository = CurrenciesRepository.getInstance();
        Optional<Currencies> usd = currenciesRepository.findByCode("USD");
        Currencies usdCurrency = usd.get();
        System.out.println(usdCurrency);
    }

    private static void findRatesById() {
        ExchangeRatesRepository exchangeRatesRepository = ExchangeRatesRepository.getInstance();
        Optional<ExchangeRates> byCode = exchangeRatesRepository.findByCode("USD", "EUR");
        ExchangeRates rate = byCode.get();
        System.out.println(rate);
    }


    private static void findAllRates() {
        ExchangeRatesRepository exchangeRatesRepository = ExchangeRatesRepository.getInstance();
        List<ExchangeRates> all = exchangeRatesRepository.findAll();
        System.out.println(all);
    }

    private static void saveTest() {
        CurrenciesRepository currenciesRepository = CurrenciesRepository.getInstance();
        Currencies currencies = new Currencies();
        currencies.setCode("KKK");
        currencies.setFullName("Valute");
        currencies.setSign("KZ");

        Currencies saveCurrencies = currenciesRepository.save(currencies);
        System.out.println(saveCurrencies);
    }
}
