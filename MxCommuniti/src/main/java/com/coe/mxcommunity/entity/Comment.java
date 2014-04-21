package com.coe.mxcommunity.entity;

import java.sql.Timestamp;

public class Comment {
	private Long id;
	
	private int owerType;	//0: ’’∆¨	1:œ‡≤·
	private Long owerId;
	
	private Long userId;
	private String content;
	private Timestamp createTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getOwerType() {
		return owerType;
	}
	public void setOwerType(int owerType) {
		this.owerType = owerType;
	}
	public Long getOwerId() {
		return owerId;
	}
	public void setOwerId(Long owerId) {
		this.owerId = owerId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
}
