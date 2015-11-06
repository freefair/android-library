package io.freefair.android.injection;

import io.freefair.android.util.log.Logger;
import io.freefair.android.util.log.Loggers;

/**
 * Created by larsgrefer on 19.10.15.
 */
public class DefaultModule implements InjectionModule {
	@Override
	public void configure(InjectionContainer injectionContainer) {
		injectionContainer.registerProvider(new InjectionProvider() {
			@Override
			public boolean canProvide(Class<?> clazz) {
				return clazz.isAssignableFrom(Logger.class);
			}

			@Override
			@SuppressWarnings("unchecked")
			public <T> T provide(Class<T> clazz, Object instance, Injector injector) {
				return (T) Loggers.forObject(instance);
			}
		});
	}
}
