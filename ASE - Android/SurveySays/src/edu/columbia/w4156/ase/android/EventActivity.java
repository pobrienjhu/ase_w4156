package edu.columbia.w4156.ase.android;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class EventActivity extends Activity implements VoteCategoriesReceiver {

	private static final String TAG = EventActivity.class.getSimpleName();
	public static final String ARG_EVENT_ID = "eventId";
	public static final String ARG_SESSION = "session";

	Map<Long, Long> voteCategoryToSelection;

	private Session session;
	private long eventId;
	private SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event);
		((ProgressBar) findViewById(R.id.event_progress_bar))
			.setVisibility(View.VISIBLE);
		this.voteCategoryToSelection = new HashMap<Long, Long>();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Bundle extras = getIntent().getExtras();
		this.session = (Session) extras.getSerializable(ARG_SESSION);
		this.eventId = extras.getLong(ARG_EVENT_ID);
		sharedPreferences = getApplicationContext()
				.getSharedPreferences("surveySays", Activity.MODE_PRIVATE);

		FetchVoteCategoriesArgs args = new FetchVoteCategoriesArgs(
				sharedPreferences.getString("serverUrl", null), this,
				session, eventId);
		FetchVoteCategoriesTask fetchTask = new FetchVoteCategoriesTask();
		fetchTask.execute(args);
	}

	@Override
	public void receiveVoteCategories(List<VoteCategory> categories) {
		((ProgressBar) findViewById(R.id.event_progress_bar))
		.setVisibility(View.GONE);
		ListView categoryListView =
				(ListView) findViewById(R.id.event_vote_category_listview); 

		voteCategoryToSelection.clear();
		for (VoteCategory category : categories) {
			voteCategoryToSelection.put(category.getCategoryId(), null);
		}

		VoteCategoryAdapter adapter = new VoteCategoryAdapter(this, categories,
				voteCategoryToSelection);
		categoryListView.setVisibility(View.VISIBLE);
		categoryListView.setAdapter(adapter);
		((Button) findViewById(R.id.event_vote_button))
			.setVisibility(View.VISIBLE);
	}

	private class FetchVoteCategoriesArgs {
		private URI uri;
		private VoteCategoriesReceiver receiver;
		private Session session;

		public URI getUri() {
			return uri;
		}

		public VoteCategoriesReceiver getReceiver() {
			return receiver;
		}

		public Session getSession() {
			return session;
		}

		public FetchVoteCategoriesArgs(String baseUrl,
				VoteCategoriesReceiver receiver, Session session, long eventId) {
			this.uri =
					URI.create(baseUrl + "/app/getEvent.do?eventId=" + eventId);
			this.receiver = receiver;
			this.session = session;
		}

	}

	private class FetchVoteCategoriesTask extends
		AsyncTask<FetchVoteCategoriesArgs, Void, List<VoteCategory>> {

		private VoteCategoriesReceiver receiver;

		@Override
		protected List<VoteCategory> doInBackground(
				FetchVoteCategoriesArgs... params) {
			List<VoteCategory> results = new ArrayList<VoteCategory>();
			FetchVoteCategoriesArgs args = params[0];

			this.receiver = args.getReceiver();
			URI uri = args.getUri();

			HttpClient client = Common.getHttpClient(uri, args.getSession());
			HttpGet get = new HttpGet(uri);

			try {
				HttpResponse response = client.execute(get);
				JSONObject event = new JSONObject(
					Common.inputStreamToString(
							response.getEntity().getContent()));
				JSONArray categoryArray = event.getJSONArray("voteCategories");
				for (int i = 0; i < categoryArray.length(); i++) {
					JSONObject categoryObject = categoryArray.getJSONObject(i);
					VoteCategory category = new VoteCategory(
							categoryObject.getLong("id"),
							categoryObject.getString("categoryName"),
							categoryObject.getString("description"));
					JSONArray optionsArray =
							categoryObject.getJSONArray("voteOptions");
					for (int j = 0; j < optionsArray.length(); j++) {
						JSONObject optionObject = optionsArray.getJSONObject(j);
						VoteOption option = new VoteOption(
								category.getCategoryId(),
								optionObject.getLong("id"),
								optionObject.getString("optionName"));
						category.addVoteOption(option);
					}
					results.add(category);
				}
			} catch (IOException e) {
				Log.w(TAG, e.getMessage());
			} catch (JSONException e) {
				Log.w(TAG, e.getMessage());
			}

			return results;
		}

		@Override
		protected void onPostExecute(List<VoteCategory> result) {
			receiver.receiveVoteCategories(result);
		}
	}

	private class CastVoteArgs {
		private URI uri;
		private Session session;
		private Map<Long, Long> voteCategoryToSelection;

		public URI getUri() {
			return uri;
		}

		public Session getSession() {
			return session;
		}

		public Map<Long, Long> getVoteCategoryToSelection() {
			return voteCategoryToSelection;
		}

		public CastVoteArgs(String baseUrl, Session session, Long eventId,
				Map<Long, Long> voteCategoryToSelection) {
			this.uri = URI.create(baseUrl + "/app/voteEvent.do");
			this.session = session;
			this.voteCategoryToSelection = voteCategoryToSelection;
		}
	}

	private class CastVoteTask extends AsyncTask<CastVoteArgs, Void, Boolean> {

		private String buildVoteString(Map<Long, Long> map) {
			StringBuilder sb = new StringBuilder();
			sb.append(eventId);
			sb.append(" ");
			for (Map.Entry<Long, Long> entry : map.entrySet()) {
				sb.append(entry.getValue());
				sb.append(" ");
			}

			return sb.toString();
		}

		@Override
		protected Boolean doInBackground(CastVoteArgs... params) {
			CastVoteArgs args = params[0];
			Session session = args.getSession();
			URI uri = args.getUri();
			HttpClient client = Common.getHttpClient(uri, session);
			HttpPost post = new HttpPost(uri);
			post.addHeader("X-CSRF-TOKEN", session.getCsrfToken());
			boolean succeeded = false;
			try {
				HttpEntity entity =
						new StringEntity(buildVoteString(args.getVoteCategoryToSelection()));
				post.setEntity(entity);
				HttpResponse response = client.execute(post);
				if (response.getStatusLine().getStatusCode() == 200) {
					succeeded = true;
				}
			} catch (IOException e) {
				Log.w(TAG, e.getMessage());
			}
			return succeeded;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			String msg = "Vote " + (result ? "successful" : "failed");
			Toast.makeText(getApplicationContext(),
					msg, Toast.LENGTH_LONG).show();
		}
	}

	public void castVote(final View view) {
		for (Map.Entry<Long, Long> entry : voteCategoryToSelection.entrySet()) {
			if (entry.getValue() == null) {
				Toast.makeText(getApplicationContext(), "You haven't voted in"
						+ " every category", Toast.LENGTH_SHORT).show(); 
				return;
			}
		}
		CastVoteArgs args = new CastVoteArgs(
				sharedPreferences.getString("serverUrl", null),
				session, eventId, voteCategoryToSelection);
		CastVoteTask task = new CastVoteTask();
		task.execute(args);
	}

}
