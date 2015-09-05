package de.fhconfig.android.library.data.orm_light;

import java.lang.reflect.Constructor;

import de.fhconfig.android.library.data.IRepository;
import de.fhconfig.android.library.data.IRepositoryFactory;
import de.fhconfig.android.library.data.IUnitOfWork;

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
