import util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException {

        System.out.println(convert(100, 1, 4));

    }

    public static List<String> exchanges(long id){
        List<String> exchanges = new ArrayList<>();
        String sql = """
                SELECT * FROM currencies
                WHERE id = ?
                """;
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                exchanges.add(resultSet.getString("fullname"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return exchanges;
    }

    public static double convert(double sum, double baseId, double targetId){
        double rate = 0;
        String sql = """
                SELECT * FROM exchangerates
                WHERE basecurrencyid = ? AND  targetcurrencyid = ?;
                """;

        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, baseId);
            statement.setDouble(2, targetId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                rate = resultSet.getDouble("rate");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return sum * rate;
    }
}
