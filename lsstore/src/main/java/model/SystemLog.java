package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import utils.DBConnection;

/**
 * Logs user actions to the system_log table in the DB.
 * Used for auditing and traceability of system usage.
 * 
 * Table: system_log (
 *     id, user_id, action, entity, message, log_level, log_date
 * )
 * 
 * @author ls
 */
public class SystemLog {

    /**
     * Logs a user action into the database system_log table.
     * 
     * @param userId   the user ID performing the action
     * @param action   short action type (e.g., LOGIN, ADD_PRODUCT)
     * @param entity   part of the system affected (e.g., PRODUCT, SALE)
     * @param message  human-readable explanation
     * @param level    log level (INFO, WARN, ERROR)
     */
    public static void log(int userId, String action, String entity, String message, String level) {
        String sql = "INSERT INTO system_log (user_id, action, entity, message, log_level, log_date) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setString(2, action);
            stmt.setString(3, entity);
            stmt.setString(5, message);
            stmt.setString(6, level);
            stmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));

            stmt.executeUpdate();

        } catch (SQLException e) {
            // You can route this to LSStoreLogger as fallback if desired
            System.err.println("Failed to log action to system_log: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

