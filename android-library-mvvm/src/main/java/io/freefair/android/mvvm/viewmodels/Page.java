package io.freefair.android.mvvm.viewmodels;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

public class Page
{
	public ObservableField<String> title = new ObservableField<>();
	public ObservableInt layout = new ObservableInt();
	public ObservableField<Object> model = new ObservableField<>();

	public Page(){

	}

	public Page(String title, int layout, Object model) {
		this.title.set(title);
		this.layout.set(layout);
		this.model.set(model);
	}
}
