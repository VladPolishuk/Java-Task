package Repository.Postgres.Tool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConnectionTool {
    private static final String URL = "jdbc:postgresql://localhost:5432/newspaper_management_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123321884433";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}