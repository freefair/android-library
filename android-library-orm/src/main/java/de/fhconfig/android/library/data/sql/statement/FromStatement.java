package de.fhconfig.android.library.data.sql.statement;

public interface FromStatement extends SqlStatement
{
	void addFrom(String from);
}
