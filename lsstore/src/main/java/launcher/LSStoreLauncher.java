package launcher;

import view.LSLogin;
import controller.LoginController;
import util.WindowsUtils;
import util.ConfigLoader;
import util.DBMigration;

import javax.swing.UIManager;
import java.awt.EventQueue;

/**
 *
 * @author ls
 */
public class LSStoreLauncher {

    public static void main(String[] args) {
        //Flyway migration
        DBMigration.migrate(ConfigLoader.getProperties());
        
        try {
            // Set system look and feel (e.g. Windows/Linux/macOS native theme)
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Warning: Could not set system look and feel");
            e.printStackTrace();
        }
        EventQueue.invokeLater(()->{
            LSLogin loginView = new LSLogin();
            new LoginController(loginView);
            WindowsUtils.centerWindow(loginView);
            loginView.setVisible(true);
        });
    }
}
