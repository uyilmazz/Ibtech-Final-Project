package com.ibtech.core.utilities.helper;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class WebHelper {
	public static InputStream get(String address) throws IOException {
		URL url = new URL(address);
		URLConnection connection = url.openConnection();
		return connection.getInputStream();
	}

	public static URLConnection connect(String address) throws MalformedURLException, IOException, UnsupportedEncodingException {
		URL url = new URL(address);
		URLConnection connection = url.openConnection();
		connection.setDoInput(true);
		connection.setDoOutput(true);
		return connection;
	}
}
