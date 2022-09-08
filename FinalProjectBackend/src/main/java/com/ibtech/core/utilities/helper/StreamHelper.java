package com.ibtech.core.utilities.helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class StreamHelper {
	public static String read(InputStream in) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line;
		StringBuilder builder = new StringBuilder();
		while((line = reader.readLine()) != null) {
			builder.append(line).append("\r\n");
		}
		String text = builder.toString();
		reader.close();
		return text;
	}
	
	public static void write(OutputStream out,String output) throws IOException, UnsupportedEncodingException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out,"utf-8"));
		writer.write(output+"\r\n");
		writer.flush();
		out.flush();
		out.close();
		writer.close();
	}
}
