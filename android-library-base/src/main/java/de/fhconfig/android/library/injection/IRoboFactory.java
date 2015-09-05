package de.fhconfig.android.library.injection;

public interface IRoboFactory
{
	boolean canCreate(Class<?> clazz);
	Object create(Class<?> clazz, RoboContainer container);
}
