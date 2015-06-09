package de.fhconfig.android.library.data.sql.exceptions;

/**
 * Created by Dennis on 09.06.2015.
 */
public class SyntaxErrorException extends RuntimeException {
	public SyntaxErrorException(String reason) {
		super(reason);
	}
}
