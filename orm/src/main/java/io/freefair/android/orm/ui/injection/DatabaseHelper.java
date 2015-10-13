package io.freefair.android.orm.ui.injection;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

import io.freefair.android.orm.OpenHelper;

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

	public void recreateDatabase() throws SQLException {
		for(Class<?> clazz : objects)
		{
			TableUtils.dropTable(source, clazz, true);
			TableUtils.createTable(source, clazz);
		}
	}
}
