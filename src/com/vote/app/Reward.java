package com.vote.app;

public class Reward {

	private String rewardName;
	private String rewardDate;
	private String rewardEdate;

	private String rewardDescription;
	private String imageName;

	public Reward(String rewardName, String rewardDate, String rewardEdate,
			String rewardDescription, String imageName) {
		super();
		this.rewardName = rewardName;
		this.rewardDate = rewardDate;
		this.rewardEdate = rewardEdate;
		this.rewardDescription = rewardDescription;
		this.imageName = imageName;

	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getRewardName() {
		return rewardName;
	}

	public void setRewardName(String rewardName) {
		this.rewardName = rewardName;
	}

	public String getRewardEdate() {
		return rewardEdate;
	}

	public void setRewardEdate(String rewardEdate) {
		this.rewardEdate = rewardEdate;
	}

	public String getRewardDate() {
		return rewardDate;
	}

	public void setRewardDate(String rewardDate) {
		this.rewardDate = rewardDate;
	}

	public String getRewardDescription() {
		return rewardDescription;
	}

	public void setRewardDescription(String rewardDescription) {
		this.rewardDescription = rewardDescription;
	}

	@Override
	public String toString() {
		return "Reward [rewardName=" + rewardName + ", rewardDate="
				+ rewardDate + ", rewardDescription=" + rewardDescription + "]";
	}

}
