package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <b>This class provides a logger utility.</b>
 * <p>It utilizes the Log4j logging framework to log messages for debugging purposes</p>
 */
public class LoggerManager {

    /**
     * <b>The logger instance for logging messages.</b>
     */
    public static final Logger logger = LogManager.getLogger(LoggerManager.class);

}
