package io.freefair.android.threading;

import android.graphics.Bitmap;

public interface IBitmapLoadingCompleteListener
{
	void onBitmapLoaded(Bitmap bmp);
}
