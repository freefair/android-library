package de.fhconfig.android.library.data;

import java.util.List;

public interface CrudRepository<TEntity, TKey> {
	TEntity find(TKey key);
	List<TEntity> findAll();
	long count();
	void save(TEntity entity);
	void update(TEntity entity);
	void delete(TEntity entity);
	void remove(TKey key);
}
