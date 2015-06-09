package de.fhconfig.android.library.data;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;

import de.fhconfig.android.library.data.annotation.Query;
import de.fhconfig.android.library.data.sql.SqlParser;
import java8.util.stream.StreamSupport;

public class CrudRepositoryImpl<TType, TKey> implements CrudRepository<TType, TKey> {
	private Class<TType> entityType;

	public CrudRepositoryImpl(Class<TType> entityType){
		this.entityType = entityType;
	}

	@SuppressWarnings("unchecked")
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		try {
			return method.invoke(this, args);
		} catch (IllegalArgumentException iae){
			//Method not Found on this
			return parseMethod(method.getName(), args, method);
		}
	}

	private Object parseMethod(String name, Object[] args, Method method) throws SQLException {
		Query annotation = method.getAnnotation(Query.class);
		if(annotation != null)
			return parseQuery(annotation.value(), args);
		if(name.startsWith("findBy"))
			return parseFindMethod(name.substring(6), args);
		else if (name.startsWith("findAllBy"))
			return parseFindAllMethod(name.substring(9), args);
		return null;
	}

	private Object parseQuery(String value, Object[] args) {
		//todo implement sql query parser
		return null;
	}

	private Object parseFindAllMethod(String name, Object[] args) throws SQLException {
		Dao<TType, TKey> query = query();
		if(args.length == 1)
			return query.queryForEq(name, args[0]);
		QueryBuilder<TType, TKey> builder = query.queryBuilder();

		return builder.query();
	}

	private Object parseFindMethod(String name, Object[] args) throws SQLException {
		Dao<TType, TKey> query = query();
		if(args.length == 1)
			return StreamSupport.stream(query.queryForEq(name, args[0])).findFirst().orElse(null);
		QueryBuilder<TType, TKey> builder = query.queryBuilder();

		return builder.queryForFirst();
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
