package de.fhconfig.android.library.threading;

import android.graphics.Bitmap;

public interface IBitmapLoadingCompleteListener
{
	void onBitmapLoaded(Bitmap bmp);
}