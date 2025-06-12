package utils;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;


public class DBMigration {
    private static final Logger logger = LoggerFactory.getLogger(DBMigration.class);

    public static void migrate(Properties dbProps) {
        try {
            String dbUrl = dbProps.getProperty("db.url");
            String dbUser = dbProps.getProperty("db.username");
            String dbPass = dbProps.getProperty("db.password");

            if (dbUrl == null || dbUser == null || dbPass == null) {
                throw new IllegalArgumentException("Database configuration is incomplete.");
            }

            Flyway flyway = Flyway.configure()
                    .dataSource(dbUrl, dbUser, dbPass)
                    .locations("classpath:db/migration")
                    .load();

            flyway.migrate();
            logger.info("Database migration completed successfully.");
        } catch (Exception e) {
            logger.error("Database migration failed.", e);
            throw new RuntimeException("Flyway migration failed", e);
        }
    }
}
