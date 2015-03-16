package de.larsgrefer.android.library.data.ormlight;


import de.larsgrefer.android.library.data.IRepository;
import de.larsgrefer.android.library.data.IRepositoryFactory;
import de.larsgrefer.android.library.data.IUnitOfWork;

import java.lang.reflect.Constructor;

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
