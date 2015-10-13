package io.freefair.android.orm;

import io.freefair.android.orm.orm_light.UnitOfWorkType;

public interface IUnitOfWorkFactory
{
	IUnitOfWork create();
	IUnitOfWork create(UnitOfWorkType type);
}
