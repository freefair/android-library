package io.freefair.android.orm.orm_light;

import java.lang.reflect.Constructor;

import io.freefair.android.orm.IRepository;
import io.freefair.android.orm.IRepositoryFactory;
import io.freefair.android.orm.IUnitOfWork;

public class RepositoryFactory implements IRepositoryFactory
{
	public RepositoryFactory() {}
	@Override
	public <TRepository extends IRepository> TRepository create(Class<TRepository> cls, IUnitOfWork unitOfWork) {
		try
		{
			Constructor<TRepository> constructor = cls.getConstructor(IUnitOfWork.class);
			return constructor.newInstance(unitOfWork);
		}
		catch (Exception ex)
		{
			return null;
		}
	}
}
