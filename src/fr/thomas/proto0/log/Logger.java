package fr.thomas.proto0.log;

public class Logger {

	private boolean isEnabled;
	private boolean enabledDebugLog;

	private final ELogLevel DEFAULT_LOG_LEVEL = ELogLevel.INFO;
	
	/**
	 * Create a new logger, enabled by default
	 * @param isEnabled
	 */
	public Logger(boolean isEnabled) {
		this.isEnabled = isEnabled;
		this.enabledDebugLog = false;
	}

	/**
	 * Log things in console with default log level, which is INFO
	 * @param data
	 */
	public void log(String data) {
		log(data, DEFAULT_LOG_LEVEL);
	}

	/**
	 * Log things in console with specific log level.
	 * @param data
	 * @param logLevel
	 */
	public void log(String data, ELogLevel logLevel) {
		if (isEnabled) {
			if (!enabledDebugLog && logLevel != ELogLevel.DEBUG)
				return;
			System.out.println("[" + logLevel.getHeader() + "] " + data);
		}
	}
	
	/**
	 * Disable or enable main logger
	 * @param state
	 */
	public void enableLogger(boolean state) {
		this.isEnabled = state;
	}
	
	/**
	 * Disable or enable debug logs
	 * @param newState
	 */
	public void updateDebugLogState(boolean newState) {
		this.enabledDebugLog = newState;
	}
}
