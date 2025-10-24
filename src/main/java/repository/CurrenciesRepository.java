package repository;

import exception.DaoException;
import model.Currencies;
import util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrenciesRepository {

    private static final CurrenciesRepository INSTANSE = new CurrenciesRepository();
    private static final String DELETE_SQL = """
            DELETE FROM currencies
            WHERE id = ?
            """;
    private static final String SAVE_SQL = """
            INSERT INTO currencies(code, fullname, sign) 
            VALUES (?, ?, ?)
            """;

    private static final String UPDATE_SQL = """
            UPDATE currencies
            SET code = ?,
                fullname = ?,
                sign = ?
            WHERE id = ?
            """;

    private static final String FIND_BY_ID_SQL = """
            SELECT id, code, fullname, sign
            FROM currencies
            WHERE id = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id, code, fullname, sign
            FROM currencies
            """;

    public CurrenciesRepository() {
    }

    public List<Currencies> findAll() {
        List<Currencies> currencies = new ArrayList<>();
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Currencies currency = null;
            while (resultSet.next()) {
                currency = buildCurrency(resultSet);
                currencies.add(currency);
            }
            return currencies;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Currencies> findById (Long id) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Currencies currency = null;
            if (resultSet.next()) {
                currency = buildCurrency(resultSet);
            }
            return Optional.ofNullable(currency);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void update(Currencies currencies) {
        try (Connection connection = ConnectionManager.open();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, currencies.getCode());
            preparedStatement.setString(2, currencies.getFullName());
            preparedStatement.setString(3, currencies.getCode());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Currencies save(Currencies currencies) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, currencies.getCode());
            preparedStatement.setString(2, currencies.getFullName());
            preparedStatement.setString(3, currencies.getSign());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                currencies.setId(generatedKeys.getLong("id"));
            }

            return currencies;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean delete(Long id) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static Currencies buildCurrency(ResultSet resultSet) throws SQLException {
        return new Currencies(resultSet.getLong("id"),
                resultSet.getString("code"),
                resultSet.getString("fullname"),
                resultSet.getString("sign"));
    }

    public static CurrenciesRepository getInstance() {
        return INSTANSE;
    }
}
