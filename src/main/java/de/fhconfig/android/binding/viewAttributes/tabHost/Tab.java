package de.fhconfig.android.binding.viewAttributes.tabHost;

import de.fhconfig.android.binding.ConstantObservable;
import de.fhconfig.android.binding.Observable;
import de.fhconfig.android.binding.observables.IntegerObservable;
import de.fhconfig.android.binding.observables.StringObservable;
import android.graphics.drawable.Drawable;

public class Tab {
	public StringObservable Activity = new StringObservable();
	public IntegerObservable ViewId = new IntegerObservable();
	public final ConstantObservable<String> Tag;
	public Observable<CharSequence> Label = new Observable<CharSequence>(CharSequence.class);
	public Observable<Drawable> Icon = new Observable<Drawable>(Drawable.class);
	
	public Tab(String tag){
		Tag = new ConstantObservable<String>(String.class, tag);
	}
}
