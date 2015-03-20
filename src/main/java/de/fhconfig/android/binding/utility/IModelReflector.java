package de.fhconfig.android.binding.utility;

import de.fhconfig.android.binding.Command;
import de.fhconfig.android.binding.IObservable;

public interface IModelReflector {
	public abstract Command getCommandByName(String name, Object object)
			throws Exception;

	public abstract IObservable<?> getObservableByName(String name,
	                                                   Object object) throws Exception;

	public abstract Object getValueByName(String name, Object object)
			throws Exception;

	public abstract Class<?> getValueTypeByName(String name);
}