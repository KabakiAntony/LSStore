package model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import utils.DBConnection;


/**
 * log user actions to the system_log table in the db
 * used for auditing and traceability of system usage
 * 
 * Table: system_log(id, username, action, level, timestamp)
 *
 * @author ls
 */
public class SystemLog {
    
    /**
    *Logs a user action into the db
    * 
    * @param username of the user performing the action
    * @param action description of the action performed
    * @param level log level(INFO, WARN, ERROR)
    */
    
    public static void log(String username, String action, String level) {
        String sql = "INSERT INTO system_log (username, action, level, timestamp) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, action);
            stmt.setString(3, level);
            stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            stmt.executeUpdate();

        } catch (SQLException e) {
            // Fallback: log this DB failure to file
            System.err.println("Failed to log system action to DB: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    
}
