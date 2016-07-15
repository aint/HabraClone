package ua.sg.academy.java2.habraclone.dbModel.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2ConnectorFactory {

    private static final String DB_URL = "jdbc:h2:mem:test";
    private static final String USER = "sa";
    private static final String PASS = "";

    private static Connection connection = null;

    public static Connection getInstance() {
        if (connection == null) {
            try {
                connection = getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

}
