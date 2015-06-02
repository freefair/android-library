package de.fhconfig.android.library.data;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;

public class CrudRepositoryImpl<TType, TKey> implements CrudRepository<TType, TKey> {
	private Class<TType> entityType;

	public CrudRepositoryImpl(Class<TType> entityType){
		this.entityType = entityType;
	}

	@SuppressWarnings("unchecked")
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		switch(method.getName()){
			case "find":
			case "findAll":
			case "count":
			case "save":
			case "update":
			case "delete":
			case "remove":
				return method.invoke(this, args);
			default:
				break;
		}
		return null;
	}

	private Dao<TType, TKey> query()
	{
		try {
			return DaoManager.createDao(SessionHelper.getInstance().getSession().getConnection(), entityType);
		} catch (Exception ex) {
			throw new NullPointerException(ex.toString());
		}
	}

	@Override
	public TType find(TKey tKey) {
		try {
			return query().queryForId(tKey);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<TType> findAll() {
		try {
			return query().queryForAll();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public long count() {
		try {
			return query().countOf();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void save(TType tType) {
		try {
			query().create(tType);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(TType tType) {
		try {
			query().update(tType);
		} catch (SQLException e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(TType tType) {
		try {
			query().delete(tType);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void remove(TKey tKey) {
		try {
			query().deleteById(tKey);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
