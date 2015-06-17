package de.fhconfig.android.library.data.sql;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;

public class SqlParser {
	public static Statement parseString(String value) {
		try {
			return CCJSqlParserUtil.parse(value);
		} catch (JSQLParserException e) {
			throw new RuntimeException(e);
		}
	}
}
