package de.fhconfig.android.library.threading;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by Dennis on 25.03.2015.
 */
public class ToastRunnable implements Runnable {
	private final Context context;
	private final CharSequence message;
	private final int duration;

	public ToastRunnable(Context context, CharSequence message, int duration) {

		this.context = context;
		this.message = message;
		this.duration = duration;
	}

	@Override
	@SuppressLint("WrongConstant")
	public void run() {
		Toast.makeText(context, message, duration).show();
	}
}
