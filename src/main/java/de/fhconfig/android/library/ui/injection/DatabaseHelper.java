package de.fhconfig.android.library.ui.injection;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.util.List;

import de.fhconfig.android.library.data.OpenHelper;

public class DatabaseHelper
{
	private static DatabaseHelper instance;
	private OpenHelper helper;
	private List<Class<?>> objects;
	private ConnectionSource source;

	protected DatabaseHelper(OpenHelper helper)
	{
		instance = this;
		this.helper = helper;
	}

	public static DatabaseHelper getInstance()
	{
		return instance;
	}

	public ConnectionSource getConnectionSource()
	{
		if(source == null)
			source = new AndroidConnectionSource(helper);
		return source;
	}

	public List<Class<?>> getObjects()
	{
		return objects;
	}

	public void registerObjects(List<Class<?>> objects) {
		this.objects = objects;
	}
}
