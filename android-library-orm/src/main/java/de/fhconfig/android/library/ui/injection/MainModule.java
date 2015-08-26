package de.fhconfig.android.library.ui.injection;

import android.content.Context;


import com.google.dexmaker.stock.ProxyBuilder;

import java.lang.reflect.Method;
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
import de.fhconfig.android.library.injection.IRoboFactory;
import de.fhconfig.android.library.injection.RoboBuilder;
import de.fhconfig.android.library.injection.RoboContainer;
import de.fhconfig.android.library.injection.RoboModule;

public class MainModule extends RoboModule {
	@Override
	public void configure(RoboBuilder builder) {
		builder.registerType(RepositoryFactory.class).to(IRepositoryFactory.class);
		builder.registerType(UnitOfWorkFactory.class).to(IUnitOfWorkFactory.class);
		builder.registerFactory(new RepositoryRoboFactory());
		builder.registerFactory(new ServiceRoboFactory());
	}

	private class RepositoryRoboFactory implements IRoboFactory {
		@Override
		public boolean canCreate(Class<?> clazz) {
			boolean assignableFrom = CrudRepository.class.isAssignableFrom(clazz);
			boolean b = clazz.getAnnotation(Repository.class) != null;
			return assignableFrom && b;
		}

		@Override
		public Object create(Class<?> clazz, RoboContainer container) {
			return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new CrudRepositoryInvocationHandler(clazz));
		}
	}

	static final Map<Class<?>, Class<?>> proxyClasses = new HashMap<>();

	private class ServiceRoboFactory implements IRoboFactory {

		@Override
		public boolean canCreate(Class<?> clazz) {
			return clazz.getAnnotation(Service.class) != null;
		}

		@Override
		public Object create(Class<?> clazz, RoboContainer container) {

			try {
				if (!proxyClasses.containsKey(clazz)) {
					Class<?> cls = ProxyBuilder.forClass(clazz)
							.dexCache(container.getContext().getDir("dx", Context.MODE_PRIVATE))
							.handler(new ServiceInvocationHandler())
							.buildProxyClass();
					proxyClasses.put(clazz, cls);
				}
				Object o = proxyClasses.get(clazz).newInstance();
				container.inject(o);
				return o;
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
	}
}
