package io.freefair.android.threading;

import android.os.Handler;

import io.freefair.android.util.logging.AndroidLogger;
import io.freefair.android.util.logging.Logger;

public abstract class UIThread implements Runnable {
	private Logger log = AndroidLogger.forClass(UIThread.class);

	private Handler _handler;
	private Thread _thread;

	public UIThread() {
		_handler = new Handler();
	}

	public void start() {
		if (_thread == null) {
			_thread = new Thread(this);
			_thread.start();
		}
	}

	public void run() {
		try {
			onRun();
			_handler.post(new Runnable() {
				@Override
				public void run() {
					UIThread.this.onFinish();
				}
			});
		} catch (final Exception ex) {
			log.error("Error while UIThread execution.", ex);
			_handler.post(new Runnable() {
				@Override
				public void run() {
					UIThread.this.onError(ex);
				}
			});
		}
	}

	public abstract void onRun();

	public abstract void onFinish();

	protected void onError(Exception ex) {

	}
}
