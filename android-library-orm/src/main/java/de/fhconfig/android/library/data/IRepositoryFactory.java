package de.fhconfig.android.library.data;

public interface IRepositoryFactory
{
	public <TRepository extends IRepository> TRepository create(Class<TRepository> cls, IUnitOfWork unitOfWork);
}
