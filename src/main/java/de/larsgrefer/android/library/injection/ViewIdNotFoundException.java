package de.larsgrefer.android.library.injection;

/**
 * Created by larsgrefer on 23.11.14.
 */
public class ViewIdNotFoundException extends Exception {

	public ViewIdNotFoundException() {
	}

	public ViewIdNotFoundException(String detailMessage) {
		super(detailMessage);
	}

	public ViewIdNotFoundException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public ViewIdNotFoundException(Throwable throwable) {
		super(throwable);
	}
}
