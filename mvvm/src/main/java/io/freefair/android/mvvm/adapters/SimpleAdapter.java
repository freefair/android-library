package io.freefair.android.mvvm.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import io.freefair.android.mvvm.BRHelper;

public class SimpleAdapter extends BaseAdapter {
	private final ListView view;
	private ObservableList<?> list;
	private int layout;

	public SimpleAdapter(ListView view, ObservableList<?> list, int layout) {
		this.view = view;
		this.list = list;
		this.layout = layout;

		setEventListener(list);
	}

	public void notifyChanged(){
		notifyDataSetInvalidated();
		notifyDataSetChanged();
	}

	private void setEventListener(ObservableList<?> list) {
		list.addOnListChangedCallback(new ObservableList.OnListChangedCallback() {
			@Override
			public void onChanged(ObservableList sender) {
				notifyChanged();
			}

			@Override
			public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
				notifyChanged();
			}

			@Override
			public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
				notifyChanged();
			}

			@Override
			public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
				notifyChanged();
			}

			@Override
			public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
				notifyChanged();
			}
		});
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewDataBinding inflate = DataBindingUtil.inflate(LayoutInflater.from(view.getContext()).cloneInContext(view.getContext()), layout, parent, false);
		inflate.setVariable(BRHelper.getBrByName("item"), list.get(position));
		return inflate.getRoot();
	}

	public void updateList(ObservableList list) {
		if(list != this.list)
		{
			setEventListener(list);
			this.list = list;
			notifyChanged();
		}
	}

	public void updateLayout(int layout) {
		if(layout != this.layout) {
			this.layout = layout;
			notifyChanged();
		}
	}
}
