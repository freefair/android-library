package de.fhconfig.android.library;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by larsgrefer on 01.02.15.
 */
public class MultiListenerHelper<T> implements MultiListenerHost<T> {

	Class<T> listenerClass;
	Set<T> listenerSet;
	T proxyListener;

	@SuppressWarnings("unchecked")
	public MultiListenerHelper(Class<T> listenerClass) {
		listenerSet = new HashSet<>();
		this.listenerClass = listenerClass;
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
	public void addListeners(T... listeners){
		for (T listener : listeners) {
			addListener(listener);
		}
	}

	@Override
	public void addListeners(Collection<T> listeners){
		listenerSet.addAll(listeners);
	}

	@Override
	public boolean removeListener(T listener) {
		return listenerSet.remove(listener);
	}

	public T getProxyListener() {
		if (proxyListener == null) {
			proxyListener = (T) Proxy.newProxyInstance(listenerClass.getClassLoader(), new Class[]{listenerClass}, new MultiListenerInvocationHandler());
		}
		return proxyListener;
	}

	private class MultiListenerInvocationHandler implements InvocationHandler {
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			if (method.getDeclaringClass().equals(MultiListenerHost.class)) {
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
