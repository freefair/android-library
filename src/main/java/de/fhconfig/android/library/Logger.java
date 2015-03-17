package de.fhconfig.android.library;

import android.util.Log;

/**
 * Created by larsgrefer on 24.11.14.
 */
public abstract class Logger {

	public abstract void verbose(String text);

	public static void verbose(Object tag, String text){
		verbose(tag.getClass(), text);
	}

	public static void verbose(Class<?> tag, String text) {
		Log.v(tag.getSimpleName(), text);
	}

	public abstract void info(String text);

	public static void info(Object object, String text) {
		info(object.getClass(), text);
	}

	public static void info(Class<?> tag, String text){
		Log.i(tag.getSimpleName(), text);
	}

	public abstract void warn(String text);

	public static void warn(Object object, String text) {
		warn(object.getClass(), text);
	}

	public static void warn(Class<?> tag, String text){
		Log.w(tag.getSimpleName(), text);
	}

	public abstract void debug(String text);

	public static void debug(Object object, String text) {
		debug(object.getClass(), text);
	}

	public static void debug(Class<?> tag, String text){
		Log.d(tag.getSimpleName(), text);
	}

	public abstract void error(String text);

	public static void error(Object object, String text) {
		error(object.getClass(), text);
	}

	public static void error(Class<?> tag, String text){
		Log.e(tag.getSimpleName(), text);
	}

	public static Logger forObject(final Object object){
		return new Logger() {
			@Override
			public void verbose(String text) {
				verbose(object, text);
			}

			@Override
			public void info(String text) {
				info(object, text);
			}

			@Override
			public void warn(String text) {
				warn(object, text);
			}

			@Override
			public void debug(String text) {
				debug(object, text);
			}

			@Override
			public void error(String text) {
				error(object, text);
			}
		};
	}

	public static Logger forClass(final Class<?> clazz){
		return new Logger() {
			@Override
			public void verbose(String text) {
				verbose(clazz, text);
			}

			@Override
			public void info(String text) {
				info(clazz, text);
			}

			@Override
			public void warn(String text) {
				warn(clazz, text);
			}

			@Override
			public void debug(String text) {
				debug(clazz, text);
			}

			@Override
			public void error(String text) {
				error(clazz, text);
			}
		};
	}
}
