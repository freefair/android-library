package de.fhconfig.android.library.ui.injection;

import de.fhconfig.android.library.data.IRepositoryFactory;
import de.fhconfig.android.library.data.IUnitOfWorkFactory;
import de.fhconfig.android.library.data.orm_light.RepositoryFactory;
import de.fhconfig.android.library.data.orm_light.UnitOfWorkFactory;
import de.fhconfig.android.library.injection.RoboBuilder;
import de.fhconfig.android.library.injection.RoboModule;

public class MainModule extends RoboModule
{
	@Override
	public void configure(RoboBuilder builder)
	{
		builder.registerType(RepositoryFactory.class).to(IRepositoryFactory.class);
		builder.registerType(UnitOfWorkFactory.class).to(IUnitOfWorkFactory.class);
	}
}