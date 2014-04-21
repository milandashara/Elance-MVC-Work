package com.coe.mxcommunity.message.entity;

import java.sql.Timestamp;

import com.coe.mxcommunity.entity.Album;

public class Message {
	private Long id;
	
	//数据库
	private int senderType;		//0:普通用户, 1:班级, 2：系统消息
	private Long senderId;	
	private int receiverType;	//0:个人  ， 1：班级
	private Long receiverId;
	private int msgType;
	private String content;
	private String appendix;
	private Long albumId;
	private Timestamp createTime;

	//附件
	private String senderName;
	private String senderAvatar;
	private Album album = null;
	
	public Message(){
		this.msgType = 0;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getSenderType() {
		return senderType;
	}
	public void setSenderType(int senderType) {
		this.senderType = senderType;
	}
	public Long getSenderId() {
		return senderId;
	}
	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}
	public int getReceiverType() {
		return receiverType;
	}
	public void setReceiverType(int receiverType) {
		this.receiverType = receiverType;
	}
	public Long getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}
	public int getMsgType() {
		return msgType;
	}
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAppendix() {
		return appendix;
	}
	public void setAppendix(String appendix) {
		this.appendix = appendix;
	}
	public Long getAlbumId() {
		return albumId;
	}
	public void setAlbumId(Long albumId) {
		this.albumId = albumId;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getSenderAvatar() {
		return senderAvatar;
	}
	public void setSenderAvatar(String senderAvatar) {
		this.senderAvatar = senderAvatar;
	}
	public Album getAlbum() {
		return album;
	}
	public void setAlbum(Album album) {
		this.album = album;
	}
}
