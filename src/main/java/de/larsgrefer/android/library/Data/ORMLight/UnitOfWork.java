package de.larsgrefer.android.library.data.ormlight;


import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import de.larsgrefer.android.library.ui.injection.DatabaseHelper;
import de.larsgrefer.android.library.data.IUnitOfWork;

import java.sql.Savepoint;
import java.util.UUID;

public class UnitOfWork implements IUnitOfWork
{
	private UnitOfWorkType type;
	private Savepoint savepoint;
	private DatabaseConnection connection;
	private ConnectionSource connectionSource;
	private Boolean completed = false;

	public UnitOfWork(UnitOfWorkType type)
	{
		this.type = type;
		init();
	}

	private void init()
	{
		try {
			connectionSource = DatabaseHelper.getInstance().getConnectionSource();
			connection = connectionSource.getReadWriteConnection();
			if(connection.isAutoCommitSupported())
			{
				if( type == UnitOfWorkType.withTransaction) {
					if (connection.isAutoCommit())
						connection.setAutoCommit(false);
				}
				else
				{
					if(!connection.isAutoCommit())
						connection.setAutoCommit(true);
				}
			}

			if(type == UnitOfWorkType.withTransaction)
			{
				savepoint = connection.setSavePoint("SAVEPOINT_" + UUID.randomUUID().toString());
			}
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public void complete()
	{
		try {
			if (type == UnitOfWorkType.withTransaction)
			{
				connection.commit(savepoint);
				completed = true;
			}
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}

	protected ConnectionSource getConnection()
	{
		return connectionSource;
	}

	@Override
	protected void finalize() throws Throwable {
		if(!completed && savepoint != null && connection != null)
			connection.rollback(savepoint);
		//if(connection != null && !connection.isClosed())
		//	connection.close();
		super.finalize();
	}
}
