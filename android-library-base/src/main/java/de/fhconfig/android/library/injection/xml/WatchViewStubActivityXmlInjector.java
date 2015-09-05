package de.fhconfig.android.library.injection.xml;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.IdRes;
import android.support.wearable.view.WatchViewStub;
import android.view.View;

import de.fhconfig.android.library.injection.helper.RClassHelper;

public class WatchViewStubActivityXmlInjector extends XmlInjector<Activity> {

	private final WatchViewStub stub;

	public WatchViewStubActivityXmlInjector(Activity activity, WatchViewStub stub) {
		super(activity, RClassHelper.getRClassFromActivity(activity));
		this.stub = stub;
	}

	public WatchViewStubActivityXmlInjector(Activity activity, WatchViewStub stub, Class<?> rClass) {
		super(activity, rClass);
		this.stub = stub;
	}

	@Override
	protected Context getContext() {
		return stub.getContext();
	}

	@Override
	protected Resources.Theme getTheme() {
		return getObject().getTheme();
	}

	@Override
	protected View findViewById(@IdRes int id) {
		return stub.findViewById(id);
	}

}
