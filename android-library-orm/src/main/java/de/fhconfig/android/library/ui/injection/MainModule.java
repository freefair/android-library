package de.fhconfig.android.library.ui.injection;

import java.lang.reflect.Proxy;

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
			return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new CrudRepositoryInvocationHandler());
		}
	}

	private class ServiceRoboFactory implements IRoboFactory {
		@Override
		public boolean canCreate(Class<?> clazz) {
			return clazz.getAnnotation(Service.class) != null;
		}

		@Override
		public Object create(Class<?> clazz, RoboContainer container) {
			Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new ServiceInvocationHandler());
			container.inject(o);
			return o;
		}
	}
}
