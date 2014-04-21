package com.coe.mxcommunity.message.controller;

import java.util.List;

import com.coe.mxcommunity.entity.Photo;

public class MessageRequest {
	private Long groupId;
	private String content;
	private List<Photo> photos;
	
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<Photo> getPhotos() {
		return photos;
	}
	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}
	
}
