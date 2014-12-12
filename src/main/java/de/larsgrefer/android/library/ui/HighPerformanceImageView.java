package de.larsgrefer.android.library.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import de.larsgrefer.android.library.threading.BitmapLoadingThread;
import de.larsgrefer.android.library.threading.IBitmapLoadingCompleteListener;
import gueei.binding.IBindableView;
import gueei.binding.ViewAttribute;
import gueei.binding.viewAttributes.imageView.SourceViewAttribute;

public class HighPerformanceImageView extends ImageView implements IBindableView<HighPerformanceImageView>,IBitmapLoadingCompleteListener {
	public HighPerformanceImageView(Context context) {
		super(context);
	}

	public HighPerformanceImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HighPerformanceImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void setImageURI(Uri uri) {
		new BitmapLoadingThread(this, uri).start();
	}

	@Override
	public ViewAttribute<? extends View, ?> createViewAttribute(String attributeId) {
		return new SourceViewAttribute(this);
	}

	@Override
	public void onBitmapLoaded(Bitmap bmp) {
		this.setImageBitmap(bmp);
	}
}
