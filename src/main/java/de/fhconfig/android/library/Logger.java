package de.fhconfig.android.library;

import android.util.Log;

/**
 * Created by larsgrefer on 24.11.14.
 */
public abstract class Logger {

	public abstract void verbose(String text);

	public abstract void verbose(String text, Throwable throwable);

	public static void verbose(Object tag, String text) {
		verbose(tag.getClass(), text);
	}

	public static void verbose(Object tag, String text, Throwable throwable) {
		verbose(tag.getClass(), text, throwable);
	}

	public static void verbose(Class<?> tag, String text) {
		Log.v(tag.getSimpleName(), text);
	}

	public static void verbose(Class<?> tag, String text, Throwable throwable) {
		Log.v(tag.getSimpleName(), text, throwable);
	}

	public abstract void info(String text);

	public abstract void info(String text, Throwable throwable);

	public static void info(Object tag, String text) {
		info(tag.getClass(), text);
	}

	public static void info(Object tag, String text, Throwable throwable) {
		info(tag.getClass(), text, throwable);
	}

	public static void info(Class<?> tag, String text) {
		Log.i(tag.getSimpleName(), text);
	}

	public static void info(Class<?> tag, String text, Throwable throwable) {
		Log.i(tag.getSimpleName(), text, throwable);
	}

	public abstract void warn(String text);

	public abstract void warn(String text, Throwable throwable);

	public static void warn(Object tag, String text) {
		warn(tag.getClass(), text);
	}

	public static void warn(Object tag, String text, Throwable throwable) {
		warn(tag.getClass(), text, throwable);
	}

	public static void warn(Class<?> tag, String text) {
		Log.w(tag.getSimpleName(), text);
	}

	public static void warn(Class<?> tag, String text, Throwable throwable) {
		Log.w(tag.getSimpleName(), text, throwable);
	}

	public abstract void debug(String text);

	public abstract void debug(String text, Throwable throwable);

	public static void debug(Object tag, String text) {
		debug(tag.getClass(), text);
	}

	public static void debug(Object tag, String text, Throwable throwable) {
		debug(tag.getClass(), text, throwable);
	}

	public static void debug(Class<?> tag, String text) {
		Log.d(tag.getSimpleName(), text);
	}

	public static void debug(Class<?> tag, String text, Throwable throwable) {
		Log.d(tag.getSimpleName(), text, throwable);
	}

	public abstract void error(String text);

	public abstract void error(String text, Throwable throwable);

	public static void error(Object tag, String text) {
		error(tag.getClass(), text);
	}

	public static void error(Object tag, String text, Throwable throwable) {
		error(tag.getClass(), text, throwable);
	}

	public static void error(Class<?> tag, String text) {
		Log.e(tag.getSimpleName(), text);
	}

	public static void error(Class<?> tag, String text, Throwable throwable) {
		Log.e(tag.getSimpleName(), text, throwable);
	}

	public static Logger forObject(final Object tag) {
		return new Logger() {

			@Override
			public void verbose(String text) {
				verbose(tag, text);
			}

			@Override
			public void verbose(String text, Throwable throwable) {
				verbose(tag, text, throwable);
			}

			@Override
			public void info(String text) {
				info(tag, text);
			}

			@Override
			public void info(String text, Throwable throwable) {
				info(tag, text, throwable);
			}

			@Override
			public void warn(String text) {
				warn(tag, text);
			}

			@Override
			public void warn(String text, Throwable throwable) {
				warn(tag, text, throwable);
			}

			@Override
			public void debug(String text) {
				debug(tag, text);
			}

			@Override
			public void debug(String text, Throwable throwable) {
				debug(tag, text, throwable);
			}

			@Override
			public void error(String text) {
				error(tag, text);
			}

			@Override
			public void error(String text, Throwable throwable) {
				error(tag, text, throwable);
			}
		};
	}

	public static Logger forClass(final Class<?> tag) {
		return new Logger() {

			@Override
			public void verbose(String text) {
				verbose(tag, text);
			}

			@Override
			public void verbose(String text, Throwable throwable) {
				verbose(tag, text, throwable);
			}

			@Override
			public void info(String text) {
				info(tag, text);
			}

			@Override
			public void info(String text, Throwable throwable) {
				info(tag, text, throwable);
			}

			@Override
			public void warn(String text) {
				warn(tag, text);
			}

			@Override
			public void warn(String text, Throwable throwable) {
				warn(tag, text, throwable);
			}

			@Override
			public void debug(String text) {
				debug(tag, text);
			}

			@Override
			public void debug(String text, Throwable throwable) {
				debug(tag, text, throwable);
			}

			@Override
			public void error(String text) {
				error(tag, text);
			}

			@Override
			public void error(String text, Throwable throwable) {
				error(tag, text, throwable);
			}
		};
	}
}
