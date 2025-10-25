package repository;

import exception.CurrencyNotFoundException;
import exception.ExchangeRateNotFoundException;
import model.Currencies;
import model.ExchangeRates;
import util.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static util.queries.ExchangeRatesQueries.FIND_ALL_SQL;
import static util.queries.ExchangeRatesQueries.FIND_BY_CODE_SQL;

public class ExchangeRatesRepository {

    private static final ExchangeRatesRepository INSTANSE = new ExchangeRatesRepository();

    public List<ExchangeRates> findAll() {
        List<ExchangeRates> exchangeRates = new ArrayList<>();
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            ExchangeRates rate = null;
            while (resultSet.next()) {
                rate = buildRates(resultSet);
                exchangeRates.add(rate);
            }
        } catch (SQLException e) {
            throw new ExchangeRateNotFoundException(e.getMessage());
        }

        return exchangeRates;
    }

    public Optional<ExchangeRates> findByCode(String baseCode, String targetCode) {
        ExchangeRates rate = null;
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_CODE_SQL)) {
            preparedStatement.setString(1, baseCode);
            preparedStatement.setString(2, targetCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                rate = buildRates(resultSet);
            }
        } catch (SQLException e) {
            throw new ExchangeRateNotFoundException(e.getMessage());
        }

        return Optional.ofNullable(rate);
    }


    private static ExchangeRates buildRates(ResultSet resultSet) throws SQLException {

        return new ExchangeRates(resultSet.getLong("id"),
                new Currencies(resultSet.getLong("baseId"),
                        resultSet.getString("baseCode"),
                        resultSet.getString("baseFullname"),
                        resultSet.getString("baseSign")),
                new Currencies(resultSet.getLong("targetId"),
                        resultSet.getString("targetCode"),
                        resultSet.getString("targetFullname"),
                        resultSet.getString("targetSign")),
                resultSet.getDouble("rate"));
    }

    public static ExchangeRatesRepository getInstance() {
        return  INSTANSE;
    }
}
