package controller;

import dao.UserDAO;
import model.User;
import util.LSStoreLogger;
import util.PasswordUtils;
import view.LSLogin;

import javax.swing.*;
import java.util.Arrays;

public class LoginController {
    private final LSLogin view;
    private final UserDAO userDAO;

    public LoginController(LSLogin view) {
        this.view = view;
        this.userDAO = new UserDAO();
        initController();
    }

    private void initController() {
        view.getLoginButton().addActionListener(e -> handleLogin());
        view.getCancelButton().addActionListener(e -> System.exit(0));
    }

    private void handleLogin() {
        String username = view.getUsername();
        char[] password = view.getPassword();

        if (username.isEmpty() || password.length == 0) {
            JOptionPane.showMessageDialog(view, "Please enter username and password.");
            return;
        }
        
        User user = null;

        try {
            user = userDAO.findByUsername(username);
            if (user != null && PasswordUtils.verifyPassword(password, user.getPassword())) {
                LSStoreLogger.info(user.getId(), "Login successful", "LOGIN_SUCCESS", null);
                JOptionPane.showMessageDialog(view, "Welcome " + username + "!");
                Arrays.fill(password, '0');
                // TODO: Open the appropriate dashboard based on role
                view.dispose(); // Close login window

            } else {
                Arrays.fill(password, '0');
                JOptionPane.showMessageDialog(view, "Invalid username or password.");
                LSStoreLogger.warn(
                    (user != null ? user.getId() : 1),
                    "Login failed for user: " + username,
                    "LOGIN_FAILED",
                    null
                );
            }
        } catch (Exception e) {
            Arrays.fill(password, '0');
            JOptionPane.showMessageDialog(view, "Error during login.");
            LSStoreLogger.error(
                (user != null ? user.getId() : 1),
                "Login exception for user: " + username,
                "LOGIN_EXCEPTION",
                "SELECT * FROM users WHERE username = ?",
                e
            );
        }
    }
}

