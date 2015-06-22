package de.fhconfig.android.library.http;

import java.net.URL;

public interface WebRequest
{
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
