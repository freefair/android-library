package de.fhconfig.android.library.ui.quick_return;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import java.util.HashSet;
import java.util.Set;

public class QuickReturnListView extends ListView implements IObservableScrollView {
	private Set<Callbacks> mCallbacks = new HashSet<>();

	public QuickReturnListView(Context context) {
		super(context);
	}

	public QuickReturnListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public QuickReturnListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@TargetApi(21)
	public QuickReturnListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (mCallbacks != null && !mCallbacks.isEmpty()) {
			for(Callbacks callbacks : mCallbacks)
				callbacks.onScrollChanged(getCurrentScrollY());
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (mCallbacks != null) {
            int action = ev.getAction();
            if(android.os.Build.VERSION.SDK_INT >= 8)
                action = ev.getActionMasked();
			switch (action) {
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

	public void addCallbacks(Callbacks listener) {
		if(mCallbacks == null)
			mCallbacks = new HashSet<>();
		mCallbacks.add(listener);
	}

	@Override
	public int getCurrentScrollY() {
		View c = getChildAt(0);
		if(c != null)
		{
			int height = c.getHeight();
			int firstVisiblePosition = getFirstVisiblePosition();
			int top = -c.getTop();
			return top + firstVisiblePosition * height;
		}
		else
			return 0;
	}

	public void removeCallbacks(Callbacks listener){
		if(mCallbacks == null)
			mCallbacks = new HashSet<>();
		mCallbacks.remove(listener);
	}
}
