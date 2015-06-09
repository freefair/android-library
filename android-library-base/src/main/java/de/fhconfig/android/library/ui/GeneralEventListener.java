package de.fhconfig.android.library.ui;

import android.view.View;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fhconfig.android.library.R;

public class GeneralEventListener
{
	private View view;
	private Map<String, List<EventListener>> listeners = new HashMap<>();

	public GeneralEventListener(View view) {
		this.view = view;
	}

	public static GeneralEventListener getByView(View view){
		Object tag = view.getTag(R.id.event_listener_id);
		if(tag == null || !(tag instanceof GeneralEventListener)){
			tag = new GeneralEventListener(view);
			view.setTag(R.id.event_listener_id, tag);
		}
		return (GeneralEventListener)tag;
	}

	public void bindEvent(String name, EventListener listener){
		String methodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1) + "Listener";
		try {
			addListener(methodName, listener);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	private void addListener(String methodName, EventListener listener) throws InvocationTargetException, IllegalAccessException {
		if(!listeners.containsKey(methodName)){
			createListener(methodName);
		}
		listeners.get(methodName).add(listener);
	}

	private void createListener(String methodName) throws InvocationTargetException, IllegalAccessException {
		Method[] methods = view.getClass().getMethods();
		for (Method method : methods) {
			if(methodName.equals(method.getName()))
			{
				Class<?> aClass = method.getParameterTypes()[0];
				Object o = Proxy.newProxyInstance(aClass.getClassLoader(), new Class[]{aClass}, new ListenerInvocationHandler(methodName));
				method.invoke(view, o);
				listeners.put(methodName, new ArrayList<>());
				return;
			}
		}
	}

	public interface EventListener {
		void invoke(View sender, Object... args) throws Throwable;
	}

	private class ListenerInvocationHandler implements InvocationHandler {
		private String methodName;

		public ListenerInvocationHandler(String methodName) {
			this.methodName = methodName;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			try {
				List<EventListener> eventListeners = listeners.get(methodName);
				for(EventListener eventListener : eventListeners){
					eventListener.invoke(view, args);
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
			return null;
		}
	}
}
