package de.larsgrefer.android.library.data.ormlight;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import de.larsgrefer.android.library.data.IRepository;
import de.larsgrefer.android.library.data.IUnitOfWork;

import java.sql.SQLException;
import java.util.List;

public class Repository<TEntity, TId> implements IRepository<TEntity, TId>
{
	private UnitOfWork unitOfWork;
	private Class<TEntity> entityClass;

	public Repository(IUnitOfWork unitOfWork, Class<TEntity> clazz)
	{
		this.unitOfWork = (UnitOfWork)unitOfWork;
		entityClass = clazz;
	}

	protected <TIEntity, TIId> Dao<TIEntity, TIId> query(Class<TIEntity> clazz)
	{
		try
		{
			return (Dao<TIEntity, TIId>) DaoManager.createDao(unitOfWork.getConnection(), clazz);
		}
		catch (Exception ex)
		{
			return null;
		}
	}

	protected Dao<TEntity, TId> query()
	{
		return query(entityClass);
	}

	@Override
	public TEntity getById(TId iId) throws SQLException {
		return this.query().queryForId(iId);
	}

	@Override
	public int count() throws SQLException {
		return (int)this.query().countOf();
	}

	@Override
	public List<TEntity> getAll() throws SQLException {
		return this.query().queryForAll();
	}

	@Override
	public void add(TEntity obj) throws SQLException {
		this.query().create(obj);
	}

	public void update(TEntity obj) throws SQLException {
		this.query().update(obj);
	}
}
