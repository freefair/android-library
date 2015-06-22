package de.fhconfig.android.library.http;

import java.net.URL;

public class WebRequestHelper
{
	public static WebRequest create(URL uri){
		return new WebRequestImpl(uri);
	}

	public static WebRequest create(String uri){
		try {
			return new WebRequestImpl(new URL(uri));
		}
		catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}
}
