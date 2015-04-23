package de.fhconfig.android.library.ui.injection;

import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class InjectionService extends Service
{
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		Application app = getApplication();
		if(app instanceof InjectionApplication)
			((InjectionApplication)app).getInjector().inject(this);

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
