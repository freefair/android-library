package io.freefair.android.orm.ui.injection;

import android.content.Context;

import com.google.dexmaker.stock.ProxyBuilder;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import io.freefair.android.injection.InjectionModule;
import io.freefair.android.injection.InjectionProvider;
import io.freefair.android.injection.injector.InjectionContainer;
import io.freefair.android.injection.injector.Injector;
import io.freefair.android.orm.CrudRepository;
import io.freefair.android.orm.CrudRepositoryInvocationHandler;
import io.freefair.android.orm.IRepositoryFactory;
import io.freefair.android.orm.IUnitOfWorkFactory;
import io.freefair.android.orm.ServiceInvocationHandler;
import io.freefair.android.orm.annotation.Repository;
import io.freefair.android.orm.annotation.Service;
import io.freefair.android.orm.orm_light.RepositoryFactory;
import io.freefair.android.orm.orm_light.UnitOfWorkFactory;

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
		public <T> T provide(Class<? super T> clazz, Object instance, Injector injector) {
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
		public <T> T provide(Class<? super T> clazz, Object instance, Injector injector) {
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
