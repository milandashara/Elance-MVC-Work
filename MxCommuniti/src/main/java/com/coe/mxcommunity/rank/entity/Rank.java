package com.coe.mxcommunity.rank.entity;

public class Rank {
	private long id;
	private long userId;
	private int rank;
	private int socialScore;
	private int livingScore;
	private int sharingScore;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public int getSocialScore() {
		return socialScore;
	}
	public void setSocialScore(int socialScore) {
		this.socialScore = socialScore;
	}

	public int getLivingScore() {
		return livingScore;
	}
	public void setLivingScore(int livingScore) {
		this.livingScore = livingScore;
	}
	public int getSharingScore() {
		return sharingScore;
	}
	public void setSharingScore(int sharingScore) {
		this.sharingScore = sharingScore;
	}
}
