package de.fhconfig.android.library;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.widget.ListView;

import java.util.Date;

import de.fhconfig.android.library.adapters.SimpleAdapter;

public class Conversions {
	@BindingAdapter({"bind:items", "bind:layout"})
	public static void listViewAdapter(ListView view, ObservableList list, int layout){
		view.setAdapter(new SimpleAdapter(view, list, layout));
	}

	@BindingConversion
	public static String convertDateToString(Date date){
		return date.toString();
	}
}
