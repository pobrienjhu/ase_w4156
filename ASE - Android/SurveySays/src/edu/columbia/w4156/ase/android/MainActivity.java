package edu.columbia.w4156.ase.android;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements
		ActionBar.OnNavigationListener, SessionReceiver {

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * current dropdown position.
	 */
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	private static final String TAG = MainActivity.class.getSimpleName();
	private static final String COOKIE_NAME = "JSESSIONID";

	private Session session;
	private Lock sessionLock;
	private SharedPreferences sharedPreferences;
	private boolean actionBarInitialized;
	private int selectedActionBarIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sessionLock = new ReentrantLock();
		selectedActionBarIndex = -1;
		actionBarInitialized = false;
	}

	private void launchSetupActivityIfNotConfigured() {
		if (!sharedPreferences.contains("authKey") ||
				!sharedPreferences.contains("serverUrl")) {
			Log.i(TAG, "no authKey or server in preferences");
			Intent intent = new Intent(this, SetupActivity.class);
			startActivity(intent);
		}

	}

	private void initializeSession() {
		String authKey = sharedPreferences.getString("authKey", null);
		LoginTask loginTask = new LoginTask(
				sharedPreferences.getString("serverUrl",
						"http://localhost/"), this);
		ProgressBar progressBar =
				(ProgressBar) findViewById(R.id.main_progress_bar);
		progressBar.setVisibility(View.VISIBLE);

		loginTask.execute(authKey);
	}

	private void setupActionBar() {
		if (actionBarInitialized) {
			return;
		}

		// Set up the action bar to show a dropdown list.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		// Set up the dropdown list navigation in the action bar.
		actionBar.setListNavigationCallbacks(
		// Specify a SpinnerAdapter to populate the dropdown list.
				new ArrayAdapter<String>(actionBar.getThemedContext(),
						android.R.layout.simple_list_item_1,
						android.R.id.text1, new String[] {
								getString(R.string.title_public_events),
								getString(R.string.title_private_events)}), this);

		if (selectedActionBarIndex != -1) {
			getActionBar().setSelectedNavigationItem(selectedActionBarIndex);
			selectedActionBarIndex = -1;
		}
		actionBarInitialized = true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		sharedPreferences =
				getApplicationContext().getSharedPreferences("surveySays",
						Context.MODE_PRIVATE);

		launchSetupActivityIfNotConfigured();
		initializeSession();
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		selectedActionBarIndex =
				savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current dropdown position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
				.getSelectedNavigationIndex());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onNavigationItemSelected(int position, long id) {
		Session fragmentSession = null;
		try {
			sessionLock.lock();
			if (session != null) {
				fragmentSession = (Session) session.clone();
			} else {
				Toast.makeText(getApplicationContext(), "Internal Error",
						Toast.LENGTH_SHORT).show();
				return false;
			}
		} catch (CloneNotSupportedException e) {
			Log.e(TAG, e.getMessage());
			Toast.makeText(getApplicationContext(), "Internal Error",
					Toast.LENGTH_SHORT).show();
			return false;
		} finally { 
			sessionLock.unlock();
		}

		Bundle args = new Bundle();
		args.putString(EventListFragment.ARG_CSRF_TOKEN,
				fragmentSession.getCsrfToken());
		args.putString(EventListFragment.ARG_SESSION_ID,
				fragmentSession.getSessionId());
		args.putBoolean(EventListFragment.ARG_IS_PUBLIC, position == 0);
		args.putString(EventListFragment.ARG_BASE_URL,
				sharedPreferences.getString("serverUrl", null));
		Fragment fragment = new EventListFragment();
		fragment.setArguments(args);
		getFragmentManager().beginTransaction()
				.replace(R.id.container, fragment).commit();
		return true;
	}


	public static class EventListFragment extends ListFragment
		implements EventListReceiver {
		public static final String ARG_CSRF_TOKEN = "csrf";
		public static final String ARG_SESSION_ID = "sessionid";
		public static final String ARG_IS_PUBLIC = "isPublic";
		public static final String ARG_BASE_URL = "baseUrl";

		private Context context;
		private Session session;
		private List<String> eventList;
		private ArrayAdapter<String> eventListAdapter;
		private View rootView;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			this.context = inflater.getContext();
			this.session = new Session(
					getArguments().getString(ARG_SESSION_ID),
					getArguments().getString(ARG_CSRF_TOKEN));

			rootView = inflater.inflate(R.layout.fragment_event_list,
					container, false);
			eventList = new ArrayList<String>();

			eventListAdapter = new ArrayAdapter<String>(context,
							android.R.layout.simple_list_item_1, eventList);
			setListAdapter(eventListAdapter);
			((ProgressBar) rootView.findViewById(R.id.event_list_progress_bar))
					.setVisibility(View.VISIBLE);

			GetEventsTask getEventsTask = new GetEventsTask();
			getEventsTask.execute(
					new GetEventsTask.GetEventsTaskArgs(session, this,
							getArguments().getBoolean(ARG_IS_PUBLIC),
							getArguments().getString(ARG_BASE_URL)));
			return rootView;
		}

		@Override
		public void receiveEvents(List<String> events) {
			eventList.clear();
			eventList.addAll(events);
			eventListAdapter.notifyDataSetChanged();
			((ProgressBar) rootView.findViewById(R.id.event_list_progress_bar))
			.setVisibility(View.GONE);
		}

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			Intent intent = new Intent(context, EventActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public boolean onOptionsItemSelected (MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent intent = new Intent(this, SetupActivity.class);
			startActivity(intent);
			return true;
		default:
			return false;
		}
	}

	@Override
	public void updateSession(Session session) {
		if (session == null) {
			Log.i(TAG, "Failed to retrieve session.");
			Toast.makeText(getApplicationContext(), "Login failed",
					Toast.LENGTH_SHORT).show();
			((TextView) findViewById(R.id.main_placeholder_text))
				.setVisibility(View.VISIBLE);
			ProgressBar progressBar =
					(ProgressBar) findViewById(R.id.main_progress_bar);
			progressBar.setVisibility(View.GONE);
			return;
		}

		String cookie = null;
		sessionLock.lock();
		try {
			this.session = session;
			cookie = session.getSessionId();
		} finally {
			sessionLock.unlock();
		}
		CheckSessionTask task = new CheckSessionTask(
				sharedPreferences.getString("serverUrl", "http://localhost"));
		task.execute(cookie);
	}

	private static Cookie createCookie(URI uri, String name, String value) {
		BasicClientCookie cookie = new BasicClientCookie(name, value);
		cookie.setPath("/");
		cookie.setDomain(uri.getHost());
		return cookie;
	}

	private static DefaultHttpClient getHttpClient(URI uri, Session session) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpClientParams.setRedirecting(httpClient.getParams(), false);
		Cookie cookie = createCookie(uri, COOKIE_NAME, session.getSessionId());
		httpClient.getCookieStore().addCookie(cookie);
		return httpClient;
	}

	private final class CheckSessionTask extends AsyncTask<String, Void, Boolean> {
		private URI checkUri;

		public CheckSessionTask(String baseUrl) {
			this(URI.create(baseUrl + "/app/authCheck.do"));
		}

		private CheckSessionTask(URI loginUrl) {
			this.checkUri = loginUrl;
		}

		@Override
		protected Boolean doInBackground(String... params) {
			boolean authPassed = false;
			DefaultHttpClient httpClient = getHttpClient(checkUri,
					new Session(params[0], null));
			HttpGet get = new HttpGet(checkUri);

			try {
				HttpResponse response = httpClient.execute(get);
				if (response.getStatusLine().getStatusCode() == 200) {
					authPassed = true;
				}
			} catch (IOException e) {
				Log.e(TAG, e.getMessage());
			}
			return authPassed;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			ProgressBar progressBar =
					(ProgressBar) findViewById(R.id.main_progress_bar);
			progressBar.setVisibility(View.GONE);

			if (Boolean.TRUE.equals(result)) {				
				((TextView) findViewById(R.id.main_placeholder_text))
					.setVisibility(View.GONE);
				setupActionBar();
			} else {
				((TextView) findViewById(R.id.main_placeholder_text))
				.setVisibility(View.VISIBLE);
			}
		}
	}
 
	private static final class GetEventsTask extends
		AsyncTask<GetEventsTask.GetEventsTaskArgs, Void, List<String>> {
		public static final class GetEventsTaskArgs {

			private String baseUrl;
			private Session session;
			private EventListReceiver receiver;
			private boolean publicEvents;

			public GetEventsTaskArgs(Session session, EventListReceiver receiver,
					boolean publicEvents, String baseUrl) {
				this.session = session;
				this.receiver = receiver;
				this.publicEvents = publicEvents;
				this.baseUrl = baseUrl;
			}

			public Session getSession() {
				return session;
			}

			public EventListReceiver getReceiver() {
				return receiver;
			}

			public boolean isPublicEvents() {
				return publicEvents;
			}

			public String getBaseUrl() {
				return baseUrl;
			}
		}

		private EventListReceiver receiver;

		@Override
		protected List<String> doInBackground(GetEventsTaskArgs... params) {
			ArrayList<String> results = new ArrayList<String>();
			GetEventsTaskArgs args = params[0];
			this.receiver = args.getReceiver();
			String controller = "getEvents" +
					(args.isPublicEvents() ? "Public" : "Private") + ".do";
			URI uri = null;
			try {
				uri = new URI(args.getBaseUrl() + "/app/" + controller);
			} catch (URISyntaxException e) {
				Log.w(TAG, e.getMessage());
				return results;
			}

			DefaultHttpClient httpClient = getHttpClient(uri,
					args.getSession());
			HttpGet get = new HttpGet(uri);
			try {
				HttpResponse response = httpClient.execute(get);
				JSONArray array = new JSONArray(
						Common.inputStreamToString(
								response.getEntity().getContent()));
				int length = array.length();
				results.ensureCapacity(length);
				for (int i = 0; i < length; i++) {
					JSONObject object = array.getJSONObject(i);
					results.add(object.getString("name"));
				}
			} catch (IOException e) {
				Log.w(TAG, e.getMessage());
			} catch (JSONException e) {
				Log.w(TAG, e.getMessage());
			}
			return results;
		}

		@Override
		protected void onPostExecute(List<String> result) {
			receiver.receiveEvents(result);
		}
	}
}
