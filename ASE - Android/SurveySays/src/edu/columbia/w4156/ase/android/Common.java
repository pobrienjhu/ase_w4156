package edu.columbia.w4156.ase.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.client.params.HttpClientParams;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;

public class Common {

	private static final String COOKIE_NAME = "JSESSIONID";

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

	private static Cookie createCookie(URI uri, String name, String value) {
		BasicClientCookie cookie = new BasicClientCookie(name, value);
		cookie.setPath("/");
		cookie.setDomain(uri.getHost());
		return cookie;
	}

	public static DefaultHttpClient getHttpClient(URI uri, Session session) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpClientParams.setRedirecting(httpClient.getParams(), false);
		Cookie cookie = createCookie(uri, COOKIE_NAME, session.getSessionId());
		httpClient.getCookieStore().addCookie(cookie);
		return httpClient;
	}
}
