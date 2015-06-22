package de.fhconfig.android.library.http;

import android.annotation.TargetApi;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;

public class WebRequestImpl implements WebRequest
{
	private URL uri;
	private HashMap<String, String> headers = new HashMap<>();
	private HashMap<String, String> formData = new HashMap<>();
	private byte[] data;
	private String method = "GET";
	private String charset = "UTF-8";

	protected WebRequestImpl(URL uri){
		this.uri = uri;
	}


	@Override
	public WebRequest addHeader(String key, String value) {
		headers.put(key, value);
		return this;
	}

	@Override
	public WebRequest setAccept(String type) {
		addHeader("Accept", type);
		return this;
	}

	@Override
	public WebRequest setMethod(String method) {
		this.method = method;
		return this;
	}

	@Override
	public WebRequest setContentType(String type) {
		addHeader("Content-Type", type);
		return this;
	}

	@Override
	public WebRequest setContentLength(long length) {
		addHeader("Content-Length", String.valueOf(length));
		return this;
	}

	@Override
	public WebRequest setData(String data) {
		try {
			setData(data.getBytes(charset));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return this;
	}

	@Override
	public WebRequest setData(byte[] data) {
		if(!this.formData.isEmpty())
			throw new RuntimeException("form data and custom data cannot be mixed");
		this.data = data;
		return this;
	}

	@Override
	public WebRequest addFormKey(String key, String value) {
		if(this.data != null)
			throw new RuntimeException("form data and custom data cannot be mixed");
		this.formData.put(key, value);
		return this;
	}

	@Override
	public String execute() {
		try {
			HttpURLConnection urlConnection = (HttpURLConnection)uri.openConnection();
			urlConnection.setRequestMethod(method);
			for (String key :
					headers.keySet()) {
				urlConnection.addRequestProperty(key, headers.get(key));
			}
			urlConnection.setDoOutput(true);
			if(!method.equals("GET") && !method.equals("DELETE"))
				urlConnection.setDoInput(true);
			if(!formData.isEmpty()){
				StringBuilder builder = new StringBuilder();
				for (String key :
						formData.keySet()) {
					builder.append(URLEncoder.encode(key, charset));
					builder.append("=");
					builder.append(URLEncoder.encode(formData.get(key), charset));
				}
				data = builder.toString().getBytes(charset);
			}
			OutputStream outputStream = urlConnection.getOutputStream();
			outputStream.write(data);
			outputStream.close();
			InputStream inputStream = urlConnection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			StringBuilder result = new StringBuilder();
			while((line = reader.readLine()) != null){
				result.append(line).append("\n");
			}
			return result.toString();
		}
		catch (IOException ex){
			throw new RuntimeException(ex);
		}
	}
}
