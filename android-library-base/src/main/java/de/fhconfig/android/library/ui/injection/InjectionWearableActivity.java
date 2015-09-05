package de.fhconfig.android.library.ui.injection;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.wearable.view.WatchViewStub;

import de.fhconfig.android.library.injection.exceptions.ViewIdNotFoundException;
import de.fhconfig.android.library.injection.xml.WatchViewStubActivityXmlInjector;

public abstract class InjectionWearableActivity extends Activity {
	WatchViewStubActivityXmlInjector injector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Application app = getApplication();
		if (app instanceof InjectionApplication)
			((InjectionApplication) app).getInjector().inject(this);
	}

	public void setContentView(int layoutResID, int viewStubID) {
		super.setContentView(layoutResID);
		final WatchViewStub stub = (WatchViewStub)findViewById(viewStubID);

		stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
			@Override
			public void onLayoutInflated(WatchViewStub stub) {
				injector = new WatchViewStubActivityXmlInjector(InjectionWearableActivity.this, stub);

				injector.injectResources();
				injector.injectAttributes();

				tryInjectViews();

				onLayoutReady();
			}
		});
	}

	private void tryInjectViews() {
		try {
			injector.injectViews();
		} catch (ViewIdNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	protected abstract void onLayoutReady();
}
