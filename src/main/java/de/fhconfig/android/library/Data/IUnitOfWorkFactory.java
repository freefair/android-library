package de.larsgrefer.android.library.data;

import de.larsgrefer.android.library.data.ormlight.UnitOfWorkType;

public interface IUnitOfWorkFactory
{
	IUnitOfWork create();
	IUnitOfWork create(UnitOfWorkType type);
}
