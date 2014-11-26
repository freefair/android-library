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
		Log.v(tag.getClass().getSimpleName(), text);
	}

	public void info(String text){
		info(object, text);
	}

	public static void info(Object tag, String text){
		Log.i(tag.getClass().getSimpleName(), text);
	}

	public void warn(String text){
		warn(object, text);
	}

	public static void warn(Object tag, String text){
		Log.w(tag.getClass().getSimpleName(), text);
	}

	public void debug(String text){
		debug(object, text);
	}

	public static void debug(Object tag, String text){
		Log.d(tag.getClass().getSimpleName(), text);
	}

	public void error(String text){
		error(object, text);
	}

	public static void error(Object tag, String text){
		Log.e(tag.getClass().getSimpleName(), text);
	}
}
