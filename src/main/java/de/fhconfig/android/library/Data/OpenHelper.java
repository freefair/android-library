package de.larsgrefer.android.library.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.table.TableUtils;
import de.fhconfig.android.library.ui.injection.DatabaseHelper;

import java.sql.SQLException;

public class OpenHelper extends android.database.sqlite.SQLiteOpenHelper {
	public OpenHelper(Context context, int version) {
		super(context, context.getPackageName() + "." + context.getClass().getSimpleName(), null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		AndroidConnectionSource source = new AndroidConnectionSource(sqLiteDatabase);
		for(Class<?> clazz : DatabaseHelper.getInstance().getObjects())
		{
			try {
				TableUtils.createTable(source, clazz);
			} catch (SQLException e) {
				Log.e(this.getClass().getName(), "Error while creating table", e);
			}
		}
		source.closeQuietly();
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
		AndroidConnectionSource source = new AndroidConnectionSource(sqLiteDatabase);
		for(Class<?> clazz : DatabaseHelper.getInstance().getObjects())
		{
			try {
				TableUtils.dropTable(source, clazz, true);
				TableUtils.createTable(source, clazz);
			} catch (SQLException e) {
				Log.e(this.getClass().getName(), "Error while creating table", e);
			}
		}
		source.closeQuietly();
	}
}
