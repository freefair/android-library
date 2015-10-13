package io.freefair.android.orm.sql;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.LateralSubSelect;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.ValuesList;
import net.sf.jsqlparser.statement.select.WithItem;

import java.sql.SQLException;

import io.freefair.android.orm.ui.injection.DatabaseHelper;

public class SqlSelectVisitor implements SelectVisitor, FromItemVisitor {
	private ConnectionSource connection;
	private Dao<?, ?> dao;
	private QueryBuilder<?, ?> queryBuilder;

	public SqlSelectVisitor(ConnectionSource connection) {
		this.connection = connection;
	}

	@Override
	public void visit(PlainSelect plainSelect) {
		queryBuilder = dao.queryBuilder();
		plainSelect.getFromItem().accept(this);
		plainSelect.getWhere().accept(new SqlWhereExpressionVisitor(queryBuilder.where()));
	}

	@Override
	public void visit(SetOperationList setOpList) {

	}

	@Override
	public void visit(WithItem withItem) {

	}

	@Override
	public void visit(Table tableName) {
		String name = tableName.getName();
		for (Class<?> clazz : DatabaseHelper.getInstance().getObjects()){
			if(clazz.getName().equals(name)){
				try {
					dao = DaoManager.createDao(connection, clazz);
					return;
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
		throw new RuntimeException("Entity " + name + " not found");
	}

	@Override
	public void visit(SubSelect subSelect) {

	}

	@Override
	public void visit(SubJoin subjoin) {

	}

	@Override
	public void visit(LateralSubSelect lateralSubSelect) {

	}

	@Override
	public void visit(ValuesList valuesList) {

	}
}
