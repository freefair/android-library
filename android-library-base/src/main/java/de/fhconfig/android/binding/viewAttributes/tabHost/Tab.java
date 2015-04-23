package de.fhconfig.android.binding.viewAttributes.tabHost;

import android.graphics.drawable.Drawable;

import de.fhconfig.android.binding.ConstantObservable;
import de.fhconfig.android.binding.Observable;
import de.fhconfig.android.binding.observables.IntegerObservable;
import de.fhconfig.android.binding.observables.StringObservable;

public class Tab {
	public final ConstantObservable<String> Tag;
	public StringObservable Activity = new StringObservable();
	public IntegerObservable ViewId = new IntegerObservable();
	public Observable<CharSequence> Label = new Observable<>(CharSequence.class);
	public Observable<Drawable> Icon = new Observable<>(Drawable.class);

	public Tab(String tag) {
		Tag = new ConstantObservable<>(String.class, tag);
	}
}
