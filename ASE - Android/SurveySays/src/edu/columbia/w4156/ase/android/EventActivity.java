package edu.columbia.w4156.ase.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class EventActivity extends Activity implements VoteCategoriesReceiver {

	public static final String ARG_EVENT_ID = "eventId";
	public static final String ARG_SESSION = "session";

	Map<Long, Long> voteCategoryToSelection;

	private Session session;
	private long eventId;

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

		FetchVoteCategoriesArgs args = new FetchVoteCategoriesArgs(this,
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
		private VoteCategoriesReceiver receiver;
		private Session session;
		private long eventId;

		public VoteCategoriesReceiver getReceiver() {
			return receiver;
		}

		public Session getSession() {
			return session;
		}

		public long getEventId() {
			return eventId;
		}

		public FetchVoteCategoriesArgs(VoteCategoriesReceiver receiver,
				Session session, long eventId) {
			super();
			this.receiver = receiver;
			this.session = session;
			this.eventId = eventId;
		}

	}

	private class FetchVoteCategoriesTask extends
		AsyncTask<FetchVoteCategoriesArgs, Void, List<VoteCategory>> {

		private VoteCategoriesReceiver receiver;

		@Override
		protected List<VoteCategory> doInBackground(
				FetchVoteCategoriesArgs... params) {
			FetchVoteCategoriesArgs args = params[0];
			this.receiver = args.getReceiver();

			List<VoteCategory> results = new ArrayList<VoteCategory>();
			for (int i = 0; i < 25; i++) {
				VoteCategory foo = new VoteCategory(i, "foo" + i,
						"foo" + i + "Desc");
				foo.addVoteOption(new VoteOption(foo.getCategoryId(), i,
						Integer.toString(i)));
				foo.addVoteOption(new VoteOption(foo.getCategoryId(), i + 1,
						Integer.toString(i + 1)));
				results.add(foo);
			}
			return results;
		}

		@Override
		protected void onPostExecute(List<VoteCategory> result) {
			receiver.receiveVoteCategories(result);
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
		Toast.makeText(getApplicationContext(), "casting vote!",
				Toast.LENGTH_SHORT).show();
	}
}
