package de.fhconfig.android.library;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.widget.ListView;

import java.util.Date;

import de.fhconfig.android.library.adapters.SimpleAdapter;
import de.fhconfig.android.binding.R;

public class Conversions {
	@BindingAdapter({"bind:items", "bind:layout"})
	public static void listViewAdapter(ListView view, ObservableList list, int layout) {
		Object tag = view.getTag(R.id.adapter_id);
		if(tag == null || !(tag instanceof SimpleAdapter))
		{
			SimpleAdapter adapter = new SimpleAdapter(view, list, layout);
			view.setTag(R.id.adapter_id, adapter);
			view.setAdapter(adapter);
			tag = adapter;
		}
		SimpleAdapter adapter = (SimpleAdapter) tag;
		adapter.updateList(list);
		adapter.updateLayout(layout);
	}

	@BindingConversion
	public static String convertDateToString(Date date){
		return date.toString();
	}
}
