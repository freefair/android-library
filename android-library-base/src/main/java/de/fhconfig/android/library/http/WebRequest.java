package de.fhconfig.android.library.http;

import java.net.URL;

public interface WebRequest
{
	static WebRequest create(URL uri){
		return new WebRequestImpl(uri);
	}

	static WebRequest create(String uri){
		try {
			return new WebRequestImpl(new URL(uri));
		}
		catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	WebRequest addHeader(String key, String value);
	WebRequest setAccept(String type);
	WebRequest setMethod(String method);
	WebRequest setContentType(String type);
	WebRequest setContentLength(long length);

	WebRequest setData(String data);
	WebRequest setData(byte[] data);
	WebRequest addFormKey(String key, String value);

	String execute();
}
