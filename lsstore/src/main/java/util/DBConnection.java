package util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static final Logger logger = LoggerFactory.getLogger(DBConnection.class);
    private static final HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        Properties dbProps = new Properties();

        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find config.properties in classpath");
            }
            dbProps.load(input);

            config.setJdbcUrl(dbProps.getProperty("db.url"));
            config.setUsername(dbProps.getProperty("db.username"));
            config.setPassword(dbProps.getProperty("db.password"));
            config.setMaximumPoolSize(Integer.parseInt(dbProps.getProperty("db.pool.maxSize", "10")));
            config.setMinimumIdle(Integer.parseInt(dbProps.getProperty("db.pool.minIdle", "2")));
            config.setIdleTimeout(Long.parseLong(dbProps.getProperty("db.pool.idleTimeout", "30000")));
            config.setMaxLifetime(Long.parseLong(dbProps.getProperty("db.pool.maxLifetime", "1800000")));
            config.setConnectionTimeout(Long.parseLong(dbProps.getProperty("db.pool.connectionTimeout", "30000")));
            config.setPoolName("LivingroomHikariPool");

            dataSource = new HikariDataSource(config);
            logger.info("Database connection pool initialized successfully.");

        } catch (Exception e) {
            logger.error("Failed to initialize database connection pool", e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = dataSource.getConnection();
        logger.debug("Acquired a new database connection from the pool.");
        return conn;
    }

    public static void shutdown() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            logger.info("Database connection pool has been shut down.");
        }
    }
}

