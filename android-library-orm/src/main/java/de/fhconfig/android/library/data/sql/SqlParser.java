package de.fhconfig.android.library.data.sql;

import java.util.List;

import de.fhconfig.android.library.data.sql.exceptions.SyntaxErrorException;
import de.fhconfig.android.library.data.sql.statement.FromStatement;
import de.fhconfig.android.library.data.sql.statement.SelectStatement;
import de.fhconfig.android.library.data.sql.statement.SqlStatement;
import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;

public class SqlParser {
	private static final String WHITESPACE_CHARS = "\\s+";
	private static final String[] KEYWORDS = new String[]{ "SELECT", "ORDER", "FROM", "WHERE" };

	public static SqlStatement parseString(String value) {
		String[] split = value.split(WHITESPACE_CHARS);
		if(split.length <= 1) throw new IllegalArgumentException("Statement is to short");
		String s = split[0];
		List<String> collect = StreamSupport.of(split).skip(1).collect(Collectors.toList());
		switch (s.toUpperCase()){
			case "UPDATE":
				parseUpdate(collect);
				break;
			case "DELETE":
				parseDelete(collect);
				break;
			case "SELECT":
				return parseSelect(collect);
			case "INSERT":
				parseInsert(collect);
				break;
		}
		return null;
	}

	private static void parseUpdate(List<String> objects) {

	}

	private static void parseDelete(List<String> objects) {

	}

	private static SelectStatement parseSelect(List<String> collect) {
		SelectStatement statement = new SelectStatement();
		int i = 0;
		int state = 0;
		for(String str : collect){
			switch (str.toUpperCase()){
				case "FROM":
					if(state != 0) throw new SyntaxErrorException("from is not allowed here");
					state = 1;
					parseFrom(StreamSupport.stream(collect).skip(i + 1).collect(Collectors.toList()), statement);
					break;
				case "ORDER":
					if(state > 2) throw new SyntaxErrorException("order is not allwed here");
					state = 3;
					parseOrderBy(StreamSupport.stream(collect).skip(i + 1).collect(Collectors.toList()), statement);
					break;
				default:
					//if(state != 0) throw new SyntaxErrorException("selection of '" + str + "' is not allowed here");
					if(state == 0) {
						String[] split = str.split(",");
						statement.addAllSelects(split);
					}
					break;
			}
			i++;
		}
		return statement;
	}

	private static void parseOrderBy(List<String> collect, SelectStatement statement) {

	}

	private static void parseFrom(List<String> collect, FromStatement statement) {
		for(String str : collect){
			if(StreamSupport.of(KEYWORDS).anyMatch(k -> str.toUpperCase().equals(k)))
				return;
			statement.addFrom(str);
		}
	}

	private static void parseInsert(List<String> collect) {

	}
}
