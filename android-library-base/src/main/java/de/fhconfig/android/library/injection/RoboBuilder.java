package de.fhconfig.android.library.injection;

import java.util.ArrayList;
import java.util.Collection;

public class RoboBuilder
{
	private ArrayList<RoboModule> modules = new ArrayList<>();
	private ArrayList<RoboRegistration<?>> registrations = new ArrayList<>();
	private ArrayList<IRoboFactory> factories = new ArrayList<>();

	public RoboBuilder()
	{

	}

	public RoboBuilder registerModule(RoboModule module)
	{
		modules.add(module);
		return this;
	}

	public RoboBuilder registerModules(Collection<RoboModule> modules)
	{
		this.modules.addAll(modules);
		return this;
	}

	public <TType> RoboRegistration<TType> registerType(Class<TType> clazz)
	{
		RoboRegistration<TType> result = new RoboRegistration<TType>(clazz);
		this.registrations.add(result);
		return result;
	}

	public void registerFactory(IRoboFactory factory){
		factories.add(factory);
	}

	public RoboContainer build()
	{
		for(RoboModule module : modules)
			module.configure(this);
		return new RoboContainer(this.registrations, this.factories);
	}
}
