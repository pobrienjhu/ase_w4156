package edu.columbia.w4156.ase.android;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.util.Log;

public class LoginTask extends AsyncTask<String, Void, Session> {
	private static final String TAG = "LoginTask"; 
	private final URI loginUri;
	private final SessionReceiver sessionReceiver;

	public LoginTask(String baseUrl, SessionReceiver receiver) {
		this(URI.create(baseUrl + "/apiLogin.do"), receiver);
	}

	private LoginTask(URI loginUrl, SessionReceiver receiver) {
		this.loginUri = loginUrl;
		this.sessionReceiver = receiver;
	}

	@Override
	protected Session doInBackground(String... params) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(loginUri.toString());
		NameValuePair method = new BasicNameValuePair("method", "api");
		NameValuePair token = new BasicNameValuePair("token", params[0]);
		ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(method);
		parameters.add(token);

		Session session = null;
		try {
			UrlEncodedFormEntity formData = new UrlEncodedFormEntity(parameters);
			post.setEntity(formData);
			HttpResponse response = client.execute(post);
			String cookie = null;
			String csrfToken = null;
			for (Header header : response.getAllHeaders()) {
				if (header.getName().equalsIgnoreCase("X-CSRF-TOKEN")) {
					csrfToken = header.getValue();
					break;
				}
			}
			for (Cookie c: client.getCookieStore().getCookies()) {
				if (c.getName().equals("JSESSIONID")) {
					cookie = c.getValue();
					break;
				}
			}
			session = new Session(cookie, csrfToken);
		} catch (UnsupportedEncodingException e) {
			Log.w(TAG, e.getMessage());
		} catch (ClientProtocolException e) {
			Log.w(TAG, e.getMessage());
		} catch (IOException e) {
			Log.w(TAG, e.getMessage());
		}

		return session;
	}

	@Override
	protected void onPostExecute(Session result) {
		sessionReceiver.updateSession(result);
	}

}
