package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import model.SystemLog;

public class LSStoreLogger {
   private static final Logger logger = LoggerFactory.getLogger(LSStoreLogger.class);

    public static void info(String username, String action) {
        logger.info("User [{}]: {}", username, action);
        SystemLog.log(username, action, "INFO");
    }

    public static void warn(String username, String action) {
        logger.warn("User [{}]: {}", username, action);
        SystemLog.log(username, action, "WARN");
    }

    public static void error(String username, String action, Throwable throwable) {
        logger.error("User [{}]: {}", username, action, throwable);
        SystemLog.log(username, action, "ERROR");
    }
    
}
