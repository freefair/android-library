package de.fhconfig.android.library.ui.quick_return;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import de.fhconfig.android.library.R;
import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.v30.app.BindingActivityV30;
import de.fhconfig.android.binding.widgets.BindableFrameLayout;

import static android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH;

@TargetApi(ICE_CREAM_SANDWICH)
public class QuickReturnFrameLayout extends BindableFrameLayout implements Callbacks {
	private enum State {
		OnScreen,
		OffScreen
	}

	private View mQuickReturnView;
	private View mPlaceholderView;
	private IObservableScrollView mObservableScrollView;
	private ScrollSettleHandler mScrollSettleHandler = new ScrollSettleHandler();
	private int mQuickReturnHeight;
	private int mMaxScrollY;

	private int scrollViewId = -1;
	private int quickReturnViewId = -1;
	private int placeholderViewId = -1;

	private static final int STATE_ONSCREEN = 0;
	private static final int STATE_OFFSCREEN = 1;
	private static final int STATE_RETURNING = 2;

	private int mMinRawY = 0;
	private int mState = STATE_ONSCREEN;

	public QuickReturnFrameLayout(Context context) {
		super(context);
		init(null);
	}

	public QuickReturnFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	public QuickReturnFrameLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}

	private void init(AttributeSet attrs)
	{
		if(attrs == null) return;
		TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.QuickReturnFrameLayout);
		scrollViewId = typedArray.getResourceId(R.styleable.QuickReturnFrameLayout_qrflScrollViewId, -1);
		quickReturnViewId = typedArray.getResourceId(R.styleable.QuickReturnFrameLayout_qrflQuickReturnViewId, -1);
		placeholderViewId = typedArray.getResourceId(R.styleable.QuickReturnFrameLayout_qrflPlaceholderViewId, -1);
        typedArray.recycle();
	}

	@Override
	protected void postBind(Object dataSource, Binder.InflateResult inflateResult) {
		BindingActivityV30 activity = (BindingActivityV30) getContext();
		mObservableScrollView = (IObservableScrollView)((inflateResult.rootView instanceof IObservableScrollView) ? inflateResult.rootView : inflateResult.rootView.findViewById(scrollViewId));
		Log.d(QuickReturnFrameLayout.class.getName(), "scrollview: " + mObservableScrollView);
		if(mObservableScrollView != null) {
			mObservableScrollView.addCallbacks(this);

			mQuickReturnView = activity.findViewById(quickReturnViewId);
			mPlaceholderView = inflateResult.rootView.findViewById(placeholderViewId);

			mObservableScrollView.getViewTreeObserver().addOnGlobalLayoutListener(
					new ViewTreeObserver.OnGlobalLayoutListener() {
						@Override
						public void onGlobalLayout() {
							try
							{
								onScrollChanged(mObservableScrollView.getCurrentScrollY());
								mMaxScrollY = mObservableScrollView.computeVerticalScrollRange()
										- mObservableScrollView.getHeight();
								mQuickReturnHeight = mPlaceholderView.getHeight();
							}
							catch (Exception ex)
							{
								Log.d(QuickReturnFrameLayout.class.getName(), "Error while global layout: ", ex);
							}
						}
					});
		}
	}

	private int oldScroll = 0;
	private int oldDirection = 1;
	@Override
	public void onScrollChanged(int scrollY) {
		if(!mObservableScrollView.hasChildren()) return;
		scrollY = Math.min(mMaxScrollY, scrollY);

		if(oldScroll < 0)
			oldScroll = 0;

		int rawY = scrollY - oldScroll;
		oldScroll = scrollY;
		int newDirection = rawY < 0 ? -1 : 1;
		if(newDirection != oldDirection)
		{
			oldDirection = newDirection;
			return;
		}

		mScrollSettleHandler.onScroll(rawY);

		float translationY = mQuickReturnView.getTranslationY();

		switch (mState) {
			case STATE_OFFSCREEN:
				if (scrollY <= mMinRawY) {
					mMinRawY = scrollY;
				} else {
					mState = STATE_RETURNING;
				}
				translationY -= rawY;
				break;

			case STATE_ONSCREEN:
				if (rawY < -mQuickReturnHeight) {
					mState = STATE_OFFSCREEN;
					mMinRawY = rawY;
				}
				translationY -= rawY;
				break;

			case STATE_RETURNING:
				translationY -= rawY;

				if (translationY >= 0) {
					mState = STATE_ONSCREEN;
				}

				if (translationY <= -mQuickReturnHeight) {
					mState = STATE_OFFSCREEN;
				}
				break;
		}

		translationY = Math.max(Math.min(translationY, 0), -mQuickReturnHeight);
		mQuickReturnView.animate().cancel();
		mQuickReturnView.setTranslationY(translationY);
	}

	@Override
	public void onDownMotionEvent() {
		onScrollSettle(false);
	}

	@Override
	public void onUpMotionEvent() {
		onScrollSettle(true);
	}

	@Override
	public void onCancelMotionEvent() {
		onScrollSettle(true);
	}

	private void onScrollSettle(boolean enabled) {
		mScrollSettleHandler.setSettleEnabled(enabled);
		mScrollSettleHandler.onScroll(mObservableScrollView.getCurrentScrollY());
	}

	private class ScrollSettleHandler extends Handler {
		private static final int SETTLE_DELAY_MILLIS = 100;

		private int mSettledScrollY = 0;
		private boolean mSettleEnabled;

		public void onScroll(int scrollY) {
			if (mSettledScrollY != scrollY) {
				// Clear any pending messages and post delayed
				removeMessages(0);
				sendEmptyMessageDelayed(0, SETTLE_DELAY_MILLIS);
				mSettledScrollY = scrollY;
			}
		}

		public void setSettleEnabled(boolean settleEnabled) {
			mSettleEnabled = settleEnabled;
		}

		@Override
		public void handleMessage(Message msg) {
			// Handle the scroll settling.
			if (mSettleEnabled) {
				int mDestTranslationY;
				if (-mQuickReturnView.getTranslationY() > mQuickReturnHeight / 2 && oldScroll > mQuickReturnHeight) {
					mState = STATE_OFFSCREEN;
					mDestTranslationY = -mQuickReturnHeight;
				} else {
					mDestTranslationY = 0;
				}

				mMinRawY = mPlaceholderView.getTop() - mQuickReturnHeight - mDestTranslationY;

				mQuickReturnView.animate().translationY(mDestTranslationY);
			}
			mSettledScrollY = Integer.MIN_VALUE; // reset
		}
	}
}

