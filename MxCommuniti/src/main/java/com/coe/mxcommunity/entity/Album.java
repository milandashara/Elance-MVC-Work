package com.coe.mxcommunity.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class Album {
	private Long id;
	
	private int ownerType;	//0:个人	 1：群组 
	private Long ownerId;		
	private int albumType;	//0:普通		1:头像
	
	private String albumName;
	private String albumDes;
	private Set<Photo> photos = new HashSet<Photo>();
	private Timestamp createTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getOwnerType() {
		return ownerType;
	}
	public void setOwnerType(int ownerType) {
		this.ownerType = ownerType;
	}
	public Long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}
	public int getAlbumType() {
		return albumType;
	}
	public void setAlbumType(int albumType) {
		this.albumType = albumType;
	}
	public String getAlbumName() {
		return albumName;
	}
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	public String getAlbumDes() {
		return albumDes;
	}
	public void setAlbumDes(String albumDes) {
		this.albumDes = albumDes;
	}
	public Set<Photo> getPhotos() {
		return photos;
	}
	public void setPhotos(Set<Photo> photos) {
		this.photos = photos;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
}
