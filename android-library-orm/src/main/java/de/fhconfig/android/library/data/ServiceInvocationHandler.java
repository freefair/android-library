package de.fhconfig.android.library.data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import de.fhconfig.android.library.data.annotation.Transactional;
import de.fhconfig.android.library.data.orm_light.UnitOfWorkType;

public class ServiceInvocationHandler implements InvocationHandler {
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		boolean success = true;
		boolean sessionExists = SessionHelper.getInstance().hasOpenSession();
		try{
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
			return method.invoke(proxy, args);
		} catch (Throwable ex) {
			success = false;
			throw ex;
		} finally {
			if(!sessionExists)
				SessionHelper.getInstance().endSession(success);
		}
	}
}
