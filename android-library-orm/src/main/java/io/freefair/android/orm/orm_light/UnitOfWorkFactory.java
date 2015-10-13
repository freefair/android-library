package io.freefair.android.orm.orm_light;

import io.freefair.android.orm.IUnitOfWork;
import io.freefair.android.orm.IUnitOfWorkFactory;

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
