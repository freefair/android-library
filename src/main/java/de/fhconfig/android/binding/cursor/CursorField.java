package de.fhconfig.android.binding.cursor;

import android.database.Cursor;

import de.fhconfig.android.binding.Observable;


public abstract class CursorField<T> extends Observable<T> {
	protected static final int COLUMN_NOT_FOUND = -1;
	protected static final int COLUMN_UNSET = -10;
	protected int mColumnIndex = COLUMN_UNSET;
	protected final String mColumnName;

	public CursorField(Class<T> type, int columnIndex) {
		super(type);
		mColumnName = null;
		mColumnIndex = columnIndex;
	}

	public CursorField(Class<T> type, String columnName) {
		super(type);
		mColumnName = columnName;
	}

	public abstract T returnValue(Cursor cursor);

	public void fillValue(Cursor cursor) {
		if (mColumnIndex >= 0) {
			this.set(returnValue(cursor));
			return;
		}
		if (mColumnIndex == COLUMN_NOT_FOUND) return;
		if (mColumnIndex == COLUMN_UNSET) {
			mColumnIndex = cursor.getColumnIndex(mColumnName);
			fillValue(cursor);
			return;
		}
	}

	// Save the data back to the database (if applicable)
	public abstract void saveValue(Cursor cursor);
}
