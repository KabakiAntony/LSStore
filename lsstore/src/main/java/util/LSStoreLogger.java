package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import model.SystemLog;

public class LSStoreLogger {
    private static final Logger logger = LoggerFactory.getLogger(LSStoreLogger.class);

    /**
     * Log an INFO level message.
     * @param userId ID of user performing the action
     * @param action Action type (e.g., LOGIN, ADD_PRODUCT)
     * @param entity Affected system entity (e.g., PRODUCT, SALE)
     * @param message Human-readable log message
     */
    public static void info(int userId, String action, String entity, String message) {
        logger.info("User [{}] Action [{}] Entity [{}] ID [{}]: {}", userId, action, entity, message);
        SystemLog.log(userId, action, entity, message, "INFO");
    }

    /**
     * Log a WARN level message.
     */
    public static void warn(int userId, String action, String entity, String message) {
        logger.warn("User [{}] Action [{}] Entity [{}] ID [{}]: {}", userId, action, entity, message);
        SystemLog.log(userId, action, entity, message, "WARN");
    }

    /**
     * Log an ERROR level message.
     */
    public static void error(int userId, String action, String entity, String message, Throwable throwable) {
        logger.error("User [{}] Action [{}] Entity [{}] ID [{}]: {}", userId, action, entity, message, throwable);
        SystemLog.log(userId, action, entity, message, "ERROR");
    }
}
