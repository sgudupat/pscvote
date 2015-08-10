package com.psc.vote.app;

public class Reward {
	
	private String rewardName;
	private String rewardDate;
	private String rewardDescription;
	public Reward(String rewardName, String rewardDate, String rewardDescription) {
		super();
		this.rewardName = rewardName;
		this.rewardDate = rewardDate;
		this.rewardDescription = rewardDescription;
	}
	public String getRewardName() {
		return rewardName;
	}
	public void setRewardName(String rewardName) {
		this.rewardName = rewardName;
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
