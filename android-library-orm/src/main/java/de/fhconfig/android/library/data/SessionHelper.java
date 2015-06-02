package de.fhconfig.android.library.data;

import de.fhconfig.android.library.data.orm_light.UnitOfWork;
import de.fhconfig.android.library.data.orm_light.UnitOfWorkType;

public class SessionHelper
{
	private ThreadLocal<UnitOfWork> session = new ThreadLocal<>();

	private SessionHelper() { }

	private static SessionHelper instance;
	protected static SessionHelper getInstance(){
		if(instance == null)
			instance = new SessionHelper();
		return instance;
	}

	public void startSession(UnitOfWorkType type){
		session.set(new UnitOfWork(type));
	}

	public UnitOfWork getSession(){
		return session.get();
	}

	public void endSession(boolean success){
		if(success)
			session.get().complete();
	}

	public boolean hasOpenSession() {
		return session.get() != null && session.get().isOpen();
	}
}
