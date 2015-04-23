package de.fhconfig.android.library.ui.quick_return;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import de.fhconfig.android.binding.IBindableView;
import de.fhconfig.android.binding.ViewAttribute;

import java.util.HashSet;
import java.util.Set;

import static android.os.Build.VERSION_CODES.FROYO;

@TargetApi(FROYO)
public class QuickReturnScrollView extends ScrollView implements IBindableView<QuickReturnScrollView>, IObservableScrollView {
	private Set<Callbacks> mCallbacks = new HashSet<>();

	public QuickReturnScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (mCallbacks != null && !mCallbacks.isEmpty()) {
			for(Callbacks callbacks : mCallbacks)
				callbacks.onScrollChanged(t);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (mCallbacks != null) {
			switch (ev.getActionMasked()) {
				case MotionEvent.ACTION_DOWN:
					for(Callbacks callbacks : mCallbacks)
						callbacks.onDownMotionEvent();
					break;
				case MotionEvent.ACTION_UP:
					for(Callbacks callbacks : mCallbacks)
						callbacks.onUpMotionEvent();
					break;
				case MotionEvent.ACTION_CANCEL:
					for(Callbacks callbacks : mCallbacks)
						callbacks.onCancelMotionEvent();
					break;
			}
		}
		return super.onTouchEvent(ev);
	}

	@Override
	public int computeVerticalScrollRange() {
		return super.computeVerticalScrollRange();
	}

	@Override
	public boolean hasChildren() {
		return getChildCount() > 0;
	}

	public int getCurrentScrollY()
	{
		return getScrollY();
	}

	public void addCallbacks(Callbacks listener) {
		if(mCallbacks == null)
			mCallbacks = new HashSet<>();
		mCallbacks.add(listener);
	}

	public void removeCallbacks(Callbacks listener){
		if(mCallbacks == null)
			mCallbacks = new HashSet<>();
		mCallbacks.remove(listener);
	}

	@Override
	public ViewAttribute<? extends View, ?> createViewAttribute(String s) {
		return null;
	}
}
