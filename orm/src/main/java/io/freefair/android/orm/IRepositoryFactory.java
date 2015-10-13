package io.freefair.android.orm;

public interface IRepositoryFactory
{
	public <TRepository extends IRepository> TRepository create(Class<TRepository> cls, IUnitOfWork unitOfWork);
}
