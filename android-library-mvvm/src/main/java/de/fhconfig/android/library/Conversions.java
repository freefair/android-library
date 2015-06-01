package de.fhconfig.android.library;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.widget.ListView;

import de.fhconfig.android.library.adapters.SimpleAdapter;

public class Conversions {
	@BindingAdapter({"bind:items", "bind:layout"})
	public static void listViewAdapter(ListView view, ObservableArrayList list, int layout){
		view.setAdapter(new SimpleAdapter(view, list, layout));
	}
}
