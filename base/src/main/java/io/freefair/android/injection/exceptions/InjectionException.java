package io.freefair.android.injection.exceptions;

/**
 * Created by larsgrefer on 29.06.15.
 */
public class InjectionException extends RuntimeException {
	public InjectionException() {
	}

	public InjectionException(String detailMessage) {
		super(detailMessage);
	}

	public InjectionException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public InjectionException(Throwable throwable) {
		super(throwable);
	}
}
