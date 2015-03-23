package de.fhconfig.android.library.data;

import de.fhconfig.android.library.data.orm_light.UnitOfWorkType;

public interface IUnitOfWorkFactory
{
	IUnitOfWork create();
	IUnitOfWork create(UnitOfWorkType type);
}
