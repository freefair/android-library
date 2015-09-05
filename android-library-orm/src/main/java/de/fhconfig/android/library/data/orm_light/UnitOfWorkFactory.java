package de.fhconfig.android.library.data.orm_light;

import de.fhconfig.android.library.data.IUnitOfWork;
import de.fhconfig.android.library.data.IUnitOfWorkFactory;

public class UnitOfWorkFactory implements IUnitOfWorkFactory
{
	public UnitOfWorkFactory() { }

	@Override
	public IUnitOfWork create()
	{
		return create(UnitOfWorkType.withoutTransaction);
	}

	@Override
	public IUnitOfWork create(UnitOfWorkType type)
	{
		return new UnitOfWork(type);
	}
}
