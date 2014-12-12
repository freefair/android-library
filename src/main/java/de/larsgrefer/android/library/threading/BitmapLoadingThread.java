package de.larsgrefer.android.library.threading;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BitmapLoadingThread implements Runnable
{
	private IBitmapLoadingCompleteListener _listener;
	private Handler _handler = new Handler();
	private Uri _uri;

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
				Log.d("com.youtube.channel.news.Threading.BitmapLoadingThread", "Error while loading file", e);
			}
		}
	}
}
