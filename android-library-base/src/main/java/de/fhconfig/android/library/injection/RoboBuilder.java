package de.fhconfig.android.library.injection;

import android.content.Context;

import com.google.common.base.Supplier;

import org.jetbrains.annotations.NotNull;

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

	public <T> void registerSupplier(@NotNull Class<T> clazz, @NotNull Supplier<T> supplier){

		factories.add(new IRoboFactory() {
			@Override
			public boolean canCreate(Class<?> clazzi) {
				return clazz.isAssignableFrom(clazzi);
			}

			@Override
			public T create(Class<?> clazz, RoboContainer container) {
				return supplier.get();
			}
		});
	}

	public RoboContainer build()
	{
		for(RoboModule module : modules)
			module.configure(this);
		return new RoboContainer(this.registrations, this.factories);
	}

	public RoboContainer build(Context context){
		for(RoboModule module : modules)
			module.configure(this);
		return new RoboContainer(context, this.registrations, this.factories);
	}
}
