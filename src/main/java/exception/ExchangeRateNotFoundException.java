package exception;

public class ExchangeRateNotFoundException extends RuntimeException {


    public ExchangeRateNotFoundException(String message) {
        super("ExchangeRate not found");
    }
}
