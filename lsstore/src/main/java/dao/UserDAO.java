package dao;

import model.Role;
import model.User;
import utils.DBConnection;
import utils.LSStoreLogger;
import utils.PasswordUtil;

import java.sql.*;

public class UserDAO {

    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    Role.valueOf(rs.getString("role"))
                );
            }
        } catch (SQLException e) {
            LSStoreLogger.error( 0, "FETCH_USER", "UserDAO", "Failed to get user: " + username, e);
        }
        return null;
    }

    public boolean create(User user) {
        String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, hashedPassword);
            stmt.setString(3, user.getRole().name());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LSStoreLogger.error( 0, "CREATE_USER", "UserDAO", "Failed to create user: " + user.getUsername(), e);

        }
        return false;
    }
}
