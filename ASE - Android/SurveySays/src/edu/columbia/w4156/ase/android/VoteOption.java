package edu.columbia.w4156.ase.android;

public class VoteOption {
	private long categoryId;
	private long optionId;
	private String name;

	public VoteOption(long categoryId, long optionId, String name) {
		super();
		this.categoryId = categoryId;
		this.optionId = optionId;
		this.name = name;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public long getOptionId() {
		return optionId;
	}

	public String getName() {
		return name;
	}
}
