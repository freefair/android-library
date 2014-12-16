package de.fhconfig.android.binding.collections;

import de.fhconfig.android.binding.Command;
import de.fhconfig.android.binding.IObservable;
import de.fhconfig.android.binding.IObservableCollection;
import de.fhconfig.android.binding.observables.BooleanObservable;
import de.fhconfig.android.binding.viewAttributes.templates.Layout;
import android.content.Context;
import android.widget.Adapter;
import android.widget.Filter;


public class Utility {
	@SuppressWarnings({ "rawtypes" })
	public static Adapter getSimpleAdapter(
			Context context, Object collection, 
			Layout layout, Layout dropDownLayout, Filter filter, String enableItemStatement, Command lastShown, BooleanObservable hasMore) throws Exception{
		if ((collection instanceof IObservableCollection)){
			IObservableCollection obsCollection = (IObservableCollection)collection;
			return new CollectionAdapter(
					context, 
					obsCollection, 
					layout, 
					dropDownLayout,
					filter, enableItemStatement,
					lastShown,
					hasMore);
		}
		if (collection instanceof IObservable){
			Class<?> clazz = ((IObservable)collection).getType();
			if (IObservableCollection.class.isAssignableFrom(clazz)){
				return new CollectionAdapter(
						context,
						(IObservableCollection)(((IObservable)collection).get()),
						layout,
						dropDownLayout,
						filter, enableItemStatement,
						lastShown,
						hasMore);
			}
		}
		return null;
	}
	
	public static Adapter getSimpleAdapter(
			Context context, Object collection, 
			Layout layout, Layout dropDownLayout, Filter filter) throws Exception{
		return getSimpleAdapter(context, collection, layout, dropDownLayout, filter, null, null, null);
	}
}
