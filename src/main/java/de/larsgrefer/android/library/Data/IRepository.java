package de.larsgrefer.android.library.data;

import java.sql.SQLException;
import java.util.List;

public interface IRepository<TEntity, TId>
{
	TEntity getById(TId id) throws SQLException;
	int count() throws SQLException;
	List<TEntity> getAll() throws SQLException;

	void add(TEntity obj) throws SQLException;
}
