package edu.columbia.w4156.ase.android;

import java.util.ArrayList;
import java.util.List;

public class VoteCategory {
	private long categoryId;
	private String name;
	private String description;

	private List<VoteOption> options;

	public VoteCategory(long categoryId, String name, String description) {
		this.categoryId = categoryId;
		this.name = name;
		this.description = description;
		this.options = new ArrayList<VoteOption>();
	}

	public long getCategoryId() {
		return categoryId;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public List<VoteOption> getOptions() {
		return options;
	}

	public void addVoteOption(VoteOption option) {
		options.add(option);
	}
}
