package edu.columbia.w4156.ase.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Common {

	public static String inputStreamToString(InputStream in) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		StringBuilder sb = new StringBuilder();
		String s;
		do {
			s = br.readLine();
			if (s != null) {
				sb.append(s);
				sb.append("\n");
			}
		} while (s != null);
		return sb.toString();
	}
}
