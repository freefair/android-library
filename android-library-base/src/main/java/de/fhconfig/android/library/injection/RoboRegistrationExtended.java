package de.fhconfig.android.library.injection;

public class RoboRegistrationExtended<TType, TIType> {
	private final Class<TType> from;
	private final Class<TIType> to;

	private boolean singleton = false;

	public RoboRegistrationExtended(Class<TType> from, Class<TIType> to) {
		this.from = from;
		this.to = to;
	}

	public RoboRegistrationExtended<TType, TIType> inSingletonScope()
	{
		singleton = true;
		return this;
	}

	public Class<?> getType() {
		return to;
	}

	public boolean isSingleton() {
		return singleton;
	}
}
