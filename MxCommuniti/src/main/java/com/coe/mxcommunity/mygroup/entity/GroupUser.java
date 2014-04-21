package com.coe.mxcommunity.mygroup.entity;

import java.io.Serializable;
import java.util.Date;

import com.coe.mxcommunity.account.entity.User;

public class GroupUser {
	public static class Id implements Serializable {
		private Long groupId;	
		private Long userId;
		
		public Id(){}
		
		public Id(Long groupId, Long userId){
			this.groupId = groupId;
			this.userId = userId;
		}

		public Long getGroupId() {
			return groupId;
		}

		public void setGroupId(Long groupId) {
			this.groupId = groupId;
		}

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}
		
	}
	
	private Id id = new Id();	
	private boolean pending = true;    
	private int role = 0;			  
	private boolean hasSeat = false;	
	private int seatX = 0;			 
	private int seatY = 0;			 
	private Date applyTime = new Date();	
	private Date addedTime = new Date();	
	
	private MyGroup myGroup;
	private User user;
	
	public GroupUser() {}
	public GroupUser(MyGroup myGroup, User user, boolean isPending){
		this.myGroup = myGroup;
		this.user = user;
		this.id.groupId = myGroup.getId();
		this.id.userId = user.getId();
		this.pending = isPending;
		
		myGroup.getGroupUsers().add(this);
		user.getGroupUsers().add(this);
	}
	public Id getId() {
		return id;
	}
	public void setId(Id id) {
		this.id = id;
	}
	public boolean isPending() {
		return pending;
	}
	public void setPending(boolean pending) {
		this.pending = pending;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public boolean isHasSeat() {
		return hasSeat;
	}
	public void setHasSeat(boolean hasSeat) {
		this.hasSeat = hasSeat;
	}
	public int getSeatX() {
		return seatX;
	}
	public void setSeatX(int seatX) {
		this.seatX = seatX;
	}
	public int getSeatY() {
		return seatY;
	}
	public void setSeatY(int seatY) {
		this.seatY = seatY;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public Date getAddedTime() {
		return addedTime;
	}
	public void setAddedTime(Date addedTime) {
		this.addedTime = addedTime;
	}
	public MyGroup getMyGroup() {
		return myGroup;
	}
	public void setMyGroup(MyGroup myGroup) {
		this.myGroup = myGroup;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
