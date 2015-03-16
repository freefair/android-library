package de.fhconfig.android.binding.viewAttributes.templates;

public class LayoutRowChild {
	private int mLayoutId = -1;
	private String mLayoutName = null;
	private String mChildDataSource = null;
	private String mColspanName = null;

	public LayoutRowChild(String childDataSource) {
		setChildDataSource(childDataSource);
	}

	public String getChildDataSource() {
		return mChildDataSource;
	}

	public void setChildDataSource(String dataSource) {
		mChildDataSource = dataSource;
	}

	public int getLayoutId() {
		return mLayoutId;
	}

	public void setLayoutId(int id) {
		mLayoutId = id;
		mLayoutName = null;
	}

	public String getLayoutName() {
		return mLayoutName;
	}

	public void setLayoutName(String name) {
		mLayoutName = name;
		mLayoutId = -1;
	}

	public String getColspanName() {
		return mColspanName;
	}

	public void setColspanName(String name) {
		mColspanName = name;
	}
}
