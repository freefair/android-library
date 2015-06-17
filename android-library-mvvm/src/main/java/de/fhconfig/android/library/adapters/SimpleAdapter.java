package de.fhconfig.android.library.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import de.fhconfig.android.binding.BR;

public class SimpleAdapter extends BaseAdapter {
	private final ListView view;
	private final ObservableArrayList<?> list;
	private final int layout;

	public SimpleAdapter(ListView view, ObservableArrayList<?> list, int layout) {
		this.view = view;
		this.list = list;
		this.layout = layout;

		list.addOnListChangedCallback(new ObservableList.OnListChangedCallback() {
			@Override
			public void onChanged(ObservableList sender) {
				SimpleAdapter.this.notifyDataSetInvalidated();
				SimpleAdapter.this.notifyDataSetChanged();
			}

			@Override
			public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
			}

			@Override
			public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {

			}

			@Override
			public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {

			}

			@Override
			public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {

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
		inflate.setVariable(1, list.get(position));
		return inflate.getRoot();
	}
}
