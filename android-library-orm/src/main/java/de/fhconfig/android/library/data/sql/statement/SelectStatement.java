package de.fhconfig.android.library.data.sql.statement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import java8.util.stream.StreamSupport;

public class SelectStatement implements FromStatement
{
	private List<String> selects = new ArrayList<>();
	private List<String> from = new ArrayList<>();

	public void addSelect(String select){
		selects.add(select);
	}

	public void addAllSelects(Collection<String> selects){
		StreamSupport.stream(selects).forEach(this::addSelect);
	}

	public void addAllSelects(String[] selects){
		for(String select : selects)
			addSelect(select);
	}

	@Override
	public void addFrom(String from) {
		this.from.add(from);
	}
}
