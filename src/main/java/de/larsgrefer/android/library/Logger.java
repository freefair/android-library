package de.larsgrefer.android.library;

import android.util.Log;

/**
 * Created by larsgrefer on 24.11.14.
 */
public class Logger {
	private static final int LOGLEVEL = Log.VERBOSE;

	private static final int VERBOSE = Log.VERBOSE;
	private static final int DEBUG = Log.DEBUG;
	private static final int INFO = Log.INFO;
	private static final int WARN = Log.WARN;
	private static final int ERROR = Log.ERROR;

	Object object;

	public Logger(Object object){
		this.object = object;
	}

	public void verbose(String text){
		if(LOGLEVEL<=VERBOSE)
			verbose(object, text);
	}

	public static void verbose(Object tag, String text){
		if(LOGLEVEL<=VERBOSE)
			verbose(tag.getClass(), text);
	}

	public static void verbose(Class<?> tag, String text) {
		if(LOGLEVEL<=VERBOSE)
			Log.v(tag.getSimpleName(), text);
	}

	public void info(String text){
		if(LOGLEVEL<=INFO)
			info(object, text);
	}

	private void info(Object object, String text) {
		if(LOGLEVEL<=INFO)
			info(object.getClass(), text);
	}

	public static void info(Class<?> tag, String text){
		if(LOGLEVEL<=INFO)
			Log.i(tag.getSimpleName(), text);
	}

	public void warn(String text){
		if(LOGLEVEL<=WARN)
			warn(object, text);
	}

	private void warn(Object object, String text) {
		if(LOGLEVEL<=WARN)
			warn(object.getClass(), text);
	}

	public static void warn(Class<?> tag, String text){
		if(LOGLEVEL<=WARN)
			Log.w(tag.getSimpleName(), text);
	}

	public void debug(String text){
		if(LOGLEVEL<=DEBUG)
			debug(object, text);
	}

	private void debug(Object object, String text) {
		if(LOGLEVEL<=DEBUG)
			debug(object.getClass(), text);
	}

	public static void debug(Class<?> tag, String text){
		if(LOGLEVEL<=DEBUG)
			Log.d(tag.getSimpleName(), text);
	}

	public void error(String text){
		if(LOGLEVEL<=ERROR)
			error(object, text);
	}

	private void error(Object object, String text) {
		if(LOGLEVEL<=ERROR)
			error(object.getClass(), text);
	}

	public static void error(Class<?> tag, String text){
		if(LOGLEVEL<=ERROR)
			Log.e(tag.getSimpleName(), text);
	}
}
