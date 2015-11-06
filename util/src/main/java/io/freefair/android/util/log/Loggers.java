package io.freefair.android.util.log;

/**
 * Created by larsgrefer on 06.11.15.
 */
public class Loggers {

	public static Logger withTag(String tag) {
		return new BaseLogger(tag);
	}

	public static Logger forClass(Class<?> clazz) {
		return new ClassLogger(clazz);
	}

	public static Logger forObject(Object object) {
		return new ObjectLogger(object);
	}
}
