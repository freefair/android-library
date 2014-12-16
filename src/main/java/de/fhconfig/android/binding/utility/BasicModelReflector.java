package de.fhconfig.android.binding.utility;

import de.fhconfig.android.binding.Command;
import de.fhconfig.android.binding.IObservable;
import de.fhconfig.android.binding.Utility;
import android.content.Context;

public class BasicModelReflector implements IModelReflector{
	private Context mContext;

	public BasicModelReflector(Context context){
		mContext = context;
	}
	public Command getCommandByName(String name, Object object)
			throws Exception {
		return Utility.getCommandForModel(name, object);
	}

	public IObservable<?> getObservableByName(String name, Object object)
			throws Exception {
		return Utility.getObservableForModel(mContext, name, object);
	}

	public Object getValueByName(String name, Object object) throws Exception {
		return null;
	}

	public Class<?> getValueTypeByName(String name) {
		return null;
	}
}
