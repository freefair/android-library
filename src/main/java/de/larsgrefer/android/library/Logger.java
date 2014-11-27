package de.larsgrefer.android.library;

import android.util.Log;

/**
 * Created by larsgrefer on 24.11.14.
 */
public class Logger {

	Object object;

	public Logger(Object object){
		this.object = object;
	}

	public void verbose(String text){
		verbose(object, text);
	}

	public static void verbose(Object tag, String text){
		verbose(tag.getClass(), text);
	}

	public static void verbose(Class<?> tag, String text) { Log.v(tag.getSimpleName(), text); }

	public void info(String text){
		info(object, text);
	}

	private void info(Object object, String text) {
		info(object.getClass(), text);
	}

	public static void info(Class<?> tag, String text){
		Log.i(tag.getSimpleName(), text);
	}

	public void warn(String text){
		warn(object, text);
	}

	private void warn(Object object, String text) {
		warn(object.getClass(), text);
	}

	public static void warn(Class<?> tag, String text){
		Log.w(tag.getSimpleName(), text);
	}

	public void debug(String text){
		debug(object, text);
	}

	private void debug(Object object, String text) {
		debug(object.getClass(), text);
	}

	public static void debug(Class<?> tag, String text){
		Log.d(tag.getSimpleName(), text);
	}

	public void error(String text){
		error(object, text);
	}

	private void error(Object object, String text) {
		error(object.getClass(), text);
	}

	public static void error(Class<?> tag, String text){
		Log.e(tag.getSimpleName(), text);
	}
}
