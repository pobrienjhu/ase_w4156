package edu.columbia.w4156.ase.android;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class VoteCategoryAdapter extends ArrayAdapter<VoteCategory> {

	private final Map<Long, Long> voteCategoryToSelection;

	public VoteCategoryAdapter(Context context,
			List<VoteCategory> voteCategories,
			Map<Long, Long> voteCategoryToSelection) {
		super(context, R.layout.votecategory_list_item, voteCategories);
		this.voteCategoryToSelection = voteCategoryToSelection;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		VoteCategory category = getItem(position);
		View view = convertView;
		if (view == null) {
			LayoutInflater li = ((Activity) getContext()).getLayoutInflater();
			view = li.inflate(R.layout.votecategory_list_item, parent, false);
		}

		TextView title =
				(TextView) view.findViewById(R.id.category_listitem_title);
		TextView description =
				(TextView) view.findViewById(R.id.category_listitem_description);
		title.setText(category.getName());
		description.setText(category.getDescription());

		RadioGroup radioGroup =
				(RadioGroup) view.findViewById(R.id.category_listitem_radiogroup);
		radioGroup.removeAllViews();
		Long selectedOption =
				voteCategoryToSelection.get(category.getCategoryId()); 
		for (VoteOption option : category.getOptions()) {
			RadioButton button = new RadioButton(getContext());
			button.setText(option.getName());
			button.setOnClickListener(new RadioButtonListener(
					category.getCategoryId(), option.getOptionId()));
			radioGroup.addView(button);
			if (selectedOption != null &&
					selectedOption == option.getOptionId()) {
				button.setChecked(true);
			}
		}

		return view;
	}

	private class RadioButtonListener implements OnClickListener {

		private long categoryId;
		private long optionId;

		public RadioButtonListener(long categoryId, long optionId) {
			this.categoryId = categoryId;
			this.optionId = optionId;
		}

		@Override
		public void onClick(View v) {
			voteCategoryToSelection.put(categoryId, optionId);
		}
	}
}
