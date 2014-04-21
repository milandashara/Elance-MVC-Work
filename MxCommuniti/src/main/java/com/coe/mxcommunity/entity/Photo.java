package com.coe.mxcommunity.entity;

import java.sql.Timestamp;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Photo {
	private Long id;
	
	@JsonIgnore
	private Album album;
	
	private Long userId;
	private String mark;
	private String photoDes;
	private String smallImgUrl;		//80 * y
	private String middleImgUrl;	//200 * y
	private String bigImgUrl;		//690 * y
	private Timestamp createTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public Album getAlbum() {
		return album;
	}
	public void setAlbum(Album album) {
		this.album = album;
	}
	public String getSmallImgUrl() {
		return smallImgUrl;
	}
	public void setSmallImgUrl(String smallImgUrl) {
		this.smallImgUrl = smallImgUrl;
	}
	public String getMiddleImgUrl() {
		return middleImgUrl;
	}
	public void setMiddleImgUrl(String middleImgUrl) {
		this.middleImgUrl = middleImgUrl;
	}
	public String getBigImgUrl() {
		return bigImgUrl;
	}
	public void setBigImgUrl(String bigImgUrl) {
		this.bigImgUrl = bigImgUrl;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getPhotoDes() {
		return photoDes;
	}
	public void setPhotoDes(String photoDes) {
		this.photoDes = photoDes;
	}
}
