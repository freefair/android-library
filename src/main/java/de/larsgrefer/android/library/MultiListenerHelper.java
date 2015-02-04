package de.larsgrefer.android.library;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by larsgrefer on 01.02.15.
 */
public class MultiListenerHelper<T> implements MultiListenerHost<T> {

	Set<T> listenerSet;

	T proxyListener;

	public MultiListenerHelper(Class<T> clazz) {
		listenerSet = new HashSet<>();
		proxyListener = (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{clazz}, new MultiListenerInvocationHandler());
	}

	@Override
	public Set<T> getListeners() {
		return listenerSet;
	}

	@Override
	public void addListener(T listener) {
		listenerSet.add(listener);
	}

	@Override
	public boolean removeListener(T listener) {
		return listenerSet.remove(listener);
	}

	public T getProxyListener() {
		return proxyListener;
	}

	private class MultiListenerInvocationHandler implements InvocationHandler {
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			if(method.getDeclaringClass().equals(MultiListenerHost.class)){
				return method.invoke(this, args);
			}

			Object result = null;
			for (T t : listenerSet) {
				result = method.invoke(t, args);
			}
			return result;
		}
	}
}
