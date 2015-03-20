package de.fhconfig.android.library.ui.quick_return;

import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;

public interface IObservableScrollView
{

	void addCallbacks(Callbacks callbacks);

	int getCurrentScrollY();

	int computeVerticalScrollRange();

	int getHeight();

	ViewTreeObserver getViewTreeObserver();

	void startAnimation(Animation a);

	void setLayoutParams(ViewGroup.LayoutParams params);

	ViewGroup.LayoutParams getLayoutParams();

	boolean hasChildren();
}
