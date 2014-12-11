package de.larsgrefer.android.library.data.ormlight;


import de.larsgrefer.android.library.data.IUnitOfWork;
import de.larsgrefer.android.library.data.IUnitOfWorkFactory;

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
