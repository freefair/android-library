package de.larsgrefer.android.library.ui;

import de.larsgrefer.android.library.data.IRepositoryFactory;
import de.larsgrefer.android.library.data.IUnitOfWorkFactory;
import de.larsgrefer.android.library.data.ormlight.RepositoryFactory;
import de.larsgrefer.android.library.data.ormlight.UnitOfWorkFactory;
import de.larsgrefer.android.library.injection.RoboBuilder;
import de.larsgrefer.android.library.injection.RoboModule;

public class MainModule extends RoboModule
{
	@Override
	public void configure(RoboBuilder builder)
	{
		builder.registerType(RepositoryFactory.class).to(IRepositoryFactory.class);
		builder.registerType(UnitOfWorkFactory.class).to(IUnitOfWorkFactory.class);
	}
}