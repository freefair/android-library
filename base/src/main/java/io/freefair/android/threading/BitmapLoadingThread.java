package io.freefair.android.threading;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import io.freefair.android.Logger;

public class BitmapLoadingThread implements Runnable
{
	private IBitmapLoadingCompleteListener _listener;
	private Handler _handler = new Handler();
	private Uri _uri;
	private Logger log = Logger.forObject(this);

	public BitmapLoadingThread(IBitmapLoadingCompleteListener listener, Uri uri)
	{
		_listener = listener;
		_uri = uri;
	}

	public void start() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		File file = new File(_uri.getPath());
		if(file.exists()) {
			try {
				final Bitmap bmp = BitmapFactory.decodeStream(new FileInputStream(file));
				_handler.post(new Runnable() {
					@Override
					public void run() {
						_listener.onBitmapLoaded(bmp);
					}
				});
			} catch (FileNotFoundException e) {
				log.debug("Error while loading file", e);
			}
		}
	}
}
