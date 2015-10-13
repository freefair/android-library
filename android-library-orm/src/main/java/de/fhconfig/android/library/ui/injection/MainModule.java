package de.fhconfig.android.library.ui.injection;

import android.content.Context;

import com.google.dexmaker.stock.ProxyBuilder;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import de.fhconfig.android.library.data.CrudRepository;
import de.fhconfig.android.library.data.CrudRepositoryInvocationHandler;
import de.fhconfig.android.library.data.IRepositoryFactory;
import de.fhconfig.android.library.data.IUnitOfWorkFactory;
import de.fhconfig.android.library.data.ServiceInvocationHandler;
import de.fhconfig.android.library.data.annotation.Repository;
import de.fhconfig.android.library.data.annotation.Service;
import de.fhconfig.android.library.data.orm_light.RepositoryFactory;
import de.fhconfig.android.library.data.orm_light.UnitOfWorkFactory;
import de.fhconfig.android.library.injection.InjectionContainer;
import de.fhconfig.android.library.injection.InjectionModule;
import de.fhconfig.android.library.injection.InjectionProvider;
import de.fhconfig.android.library.injection.Injector;

public class MainModule implements InjectionModule {

	@Override
	public void configure(InjectionContainer injectionContainer) {
		injectionContainer.registerType(RepositoryFactory.class, IRepositoryFactory.class);
		injectionContainer.registerType(UnitOfWorkFactory.class, IUnitOfWorkFactory.class);
		injectionContainer.registerProvider(new RepositoryRoboProvider());
		injectionContainer.registerProvider(new ServiceRoboProvider());
	}

	private class RepositoryRoboProvider implements InjectionProvider {
		@Override
		public boolean canProvide(Class<?> clazz) {
			boolean assignableFrom = CrudRepository.class.isAssignableFrom(clazz);
			boolean b = clazz.getAnnotation(Repository.class) != null;
			return assignableFrom && b;
		}

		@Override
		@SuppressWarnings("unchecked")
		public <T> T provide(Class<T> clazz, Object instance, Injector injector) {
			return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new CrudRepositoryInvocationHandler(clazz));
		}
	}

	static final Map<Class<?>, Class<?>> proxyClasses = new HashMap<>();

	private class ServiceRoboProvider implements InjectionProvider {

		@Override
		public boolean canProvide(Class<?> clazz) {
			return clazz.getAnnotation(Service.class) != null;
		}

		@Override
		@SuppressWarnings("unchecked")
		public <T> T provide(Class<T> clazz, Object instance, Injector injector) {
			try {
				if (!proxyClasses.containsKey(clazz)) {
					Class<?> cls = ProxyBuilder.forClass(clazz)
							.dexCache(injector.resolveValue(Context.class, instance).getDir("dx", Context.MODE_PRIVATE))
							.handler(new ServiceInvocationHandler())
							.buildProxyClass();
					proxyClasses.put(clazz, cls);
				}
				return (T) proxyClasses.get(clazz).newInstance();
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
	}
}
