package io.freefair.android.injection.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;

import io.freefair.android.injection.Injector;
import io.freefair.android.injection.InjectorProvider;
import io.freefair.android.injection.exceptions.ViewIdNotFoundException;
import io.freefair.android.injection.xml.WatchViewStubActivityInjector;

public abstract class InjectionWearableActivity extends Activity implements InjectorProvider {
	WatchViewStubActivityInjector injector;

	Injector parentInjector;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(getApplication() instanceof InjectorProvider){
			parentInjector = ((InjectorProvider)getApplication()).getInjector();
			parentInjector.inject(this);
		}
	}

	public void setContentView(int layoutResID, int viewStubID) {
		super.setContentView(layoutResID);
		final WatchViewStub stub = (WatchViewStub)findViewById(viewStubID);

		stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
			@Override
			public void onLayoutInflated(WatchViewStub stub) {
				injector = new WatchViewStubActivityInjector(InjectionWearableActivity.this, stub, parentInjector);

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

	@Override
	public WatchViewStubActivityInjector getInjector() {
		return injector;
	}
}
