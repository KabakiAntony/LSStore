package util;

import java.awt.Window;     
import java.awt.Dimension;
import java.awt.Toolkit;

public class WindowsUtils {

    public static void centerWindow(Window window) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = window.getWidth();
        int height = window.getHeight();
        int x = (screenSize.width - width) / 2;
        int y = (screenSize.height - height) / 2;
        window.setLocation(x, y);
    }
}
