package io.freefair.android.util.log;

/**
 * Created by larsgrefer on 24.11.14.
 */
public interface Logger {

	void verbose(String text);

	void verbose(String text, Throwable throwable);

	void info(String text);

	void info(String text, Throwable throwable);

	void warn(String text);

	void warn(String text, Throwable throwable);

	void debug(String text);

	void debug(String text, Throwable throwable);

	void error(String text);

	void error(String text, Throwable throwable);

}
