package util.queries;

public class CurrenciesQueries {

    public static final String DELETE_SQL = """
            DELETE FROM currencies
            WHERE id = ?
            """;
    public static final String SAVE_SQL = """
            INSERT INTO currencies(code, fullname, sign) 
            VALUES (?, ?, ?)
            """;

    public static final String UPDATE_SQL = """
            UPDATE currencies
            SET code = ?,
                fullname = ?,
                sign = ?
            WHERE id = ?
            """;

    public static final String FIND_BY_ID_SQL = """
            SELECT id, code, fullname, sign
            FROM currencies
            WHERE id = ?
            """;

    public static final String FIND_BY_CODE_SQL = """
            SELECT id, code, fullname, sign
            FROM currencies
            WHERE code = ?
            """;

    public static final String FIND_ALL_SQL = """
            SELECT id, code, fullname, sign
            FROM currencies
            """;
}
