package de.fhconfig.android.library.ui.quick_return;

public interface Callbacks {
	public void onScrollChanged(int scrollY);
	public void onDownMotionEvent();
	public void onUpMotionEvent();
	public void onCancelMotionEvent();
}
