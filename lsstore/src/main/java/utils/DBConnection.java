package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final Logger logger = LoggerFactory.getLogger(DBConnection.class);
    private static Connection connection = null;

    static {
        try {
            String url = ConfigLoader.get("db.url");
            String username = ConfigLoader.get("db.username");
            String password = ConfigLoader.get("db.password");
            String driver = ConfigLoader.get("db.driver");

            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
            logger.info("Database connection established to {}", url);

        } catch (ClassNotFoundException e) {
            logger.error("Database driver not found", e);
        } catch (SQLException e) {
            logger.error("Failed to connect to the database", e);
        } catch (Exception e) {
            logger.error("Unexpected error during DB connection setup", e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}

