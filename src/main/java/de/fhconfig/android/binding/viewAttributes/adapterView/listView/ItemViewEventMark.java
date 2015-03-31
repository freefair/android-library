package de.fhconfig.android.binding.viewAttributes.adapterView.listView;

/**
 * User: =ra=
 * Date: Dec 12, 2011
 * Time: 6:14:19 PM
 */

import android.view.View;
import android.widget.AdapterView;

import java.lang.ref.WeakReference;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.listeners.OnItemClickListenerMulticast;
import de.fhconfig.android.binding.listeners.OnItemLongClickListenerMulticast;

public class ItemViewEventMark implements View.OnClickListener, View.OnLongClickListener {

	private final WeakReference<View> mWRTargetView;
	private int mTargetListItemPosition;
	private long mTargetListItemId;

	public ItemViewEventMark(View targetView, int targetListItemPosition, long targetListItemId) {
		super();
		mTargetListItemPosition = targetListItemPosition;
		mTargetListItemId = targetListItemId;
		mWRTargetView = new WeakReference<>(targetView);
	}

	@Override
	public boolean onLongClick(View v) {
		OnItemLongClickListenerMulticast listener = getOnItemLongClickListenerMulticast();
		if (null != listener) {
			listener.onItemLongClick((AdapterView<?>) getView(), v, mTargetListItemPosition, mTargetListItemId);
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		OnItemClickListenerMulticast listener = getOnItemClickListenerMulticast();
		if (null != listener) {
			listener.onItemClick((AdapterView<?>) getView(), v, mTargetListItemPosition, mTargetListItemId);
		}
	}

	@Override
	public boolean equals(Object other) {
		boolean result = false;
		if (null != other && other instanceof ItemViewEventMark) {
			ItemViewEventMark that = (ItemViewEventMark) other;
			result = (this.mTargetListItemPosition == that.mTargetListItemPosition) && (this.mTargetListItemId == that.mTargetListItemId)
					&& (this.getView() == that.getView());
		}
		return result;
	}

	private View getView() {
		return mWRTargetView.get();
	}

	private OnItemLongClickListenerMulticast getOnItemLongClickListenerMulticast() {
		View view = getView();
		if (null != view) {
			return Binder.getMulticastListenerForView(view, OnItemLongClickListenerMulticast.class);
		}
		return null;
	}

	private OnItemClickListenerMulticast getOnItemClickListenerMulticast() {
		View view = getView();
		if (null != view) {
			return Binder.getMulticastListenerForView(view, OnItemClickListenerMulticast.class);
		}
		return null;
	}

	public void setIdAndPosition(int newPosition, long newId) {
		mTargetListItemPosition = newPosition;
		mTargetListItemId = newId;
	}

}
