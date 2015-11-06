package io.freefair.android.util.log;

import android.util.Log;

/**
 * Created by larsgrefer on 06.11.15.
 */
public class BaseLogger implements Logger {

	String tag;

	BaseLogger(String tag){
		if(tag.length() <= 23) {
			this.tag = tag;
		} else {
			this.tag = tag.substring(tag.length()-23);
		}
	}

	protected String getTag(){
		return tag;
	}

	@Override
	public void verbose(String text) {
		Log.v(getTag(), text);
	}

	@Override
	public void verbose(String text, Throwable throwable) {
		Log.v(getTag(), text, throwable);
	}

	@Override
	public void info(String text) {
		Log.i(getTag(), text);
	}

	@Override
	public void info(String text, Throwable throwable) {
		Log.i(getTag(), text, throwable);
	}

	@Override
	public void warn(String text) {
		Log.w(getTag(), text);
	}

	@Override
	public void warn(String text, Throwable throwable) {
		Log.w(getTag(), text, throwable);
	}

	@Override
	public void debug(String text) {
		Log.d(getTag(), text);
	}

	@Override
	public void debug(String text, Throwable throwable) {
		Log.d(getTag(), text, throwable);
	}

	@Override
	public void error(String text) {
		Log.e(getTag(), text);
	}

	@Override
	public void error(String text, Throwable throwable) {
		Log.e(getTag(), text, throwable);
	}
}
