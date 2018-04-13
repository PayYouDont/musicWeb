package music.util;

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;

public class LogUtil {
	public static void logError(Logger logger, Exception e) {
		logger.error(e.getMessage());
		StackTraceElement[] error = e.getStackTrace();
		for (StackTraceElement stackTraceElement : error) {
			logger.error(stackTraceElement.toString());
		}
	}

	public static void logError(Log logger, Exception e) {
		logger.error(e.getMessage());
		StackTraceElement[] error = e.getStackTrace();
		for (StackTraceElement stackTraceElement : error) {
			logger.error(stackTraceElement.toString());
		}
	}
}
