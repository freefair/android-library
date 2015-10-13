package io.freefair.android.orm;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

import io.freefair.android.orm.annotation.Transactional;
import io.freefair.android.orm.orm_light.UnitOfWorkType;
import io.freefair.android.reflection.Reflection;

public class CrudRepositoryInvocationHandler implements InvocationHandler {
	private CrudRepositoryImpl obj;

	@SuppressWarnings("unchecked")
	public CrudRepositoryInvocationHandler(Class<?> interfaceType){
		List<Class<?>> interfaces = Reflection.getActualTypeArguments(interfaceType, CrudRepository.class);
		if(interfaces == null)
			throw new IllegalArgumentException("interfaces is null");
		Class<?> entityType = interfaces.get(0);
		obj = new CrudRepositoryImpl(entityType);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		boolean success = true;
		boolean sessionExists = SessionHelper.getInstance().hasOpenSession();
		try {
			if(!sessionExists)
			{
				UnitOfWorkType type = UnitOfWorkType.withoutTransaction;
				if(method.getAnnotation(Transactional.class) != null){
					type = UnitOfWorkType.withTransaction;
				}
				if(proxy.getClass().getAnnotation(Transactional.class) != null){
					type = UnitOfWorkType.withTransaction;
				}
				SessionHelper.getInstance().startSession(type);
			}
			return obj.invoke(proxy, method, args);
		} catch (Throwable throwable){
			success = false;
			throw throwable;
		} finally {
			if(!sessionExists)
				SessionHelper.getInstance().endSession(success);
		}
	}
}
