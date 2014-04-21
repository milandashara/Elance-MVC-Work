package com.coe.mxcommunity.account.entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.coe.mxcommunity.mygroup.entity.GroupUser;


public class User {
	private Long id;
	private int code;
	private String name;
	private String email;
	
	@JsonIgnore
	private String password;	
	private int sex;
	private String bigAvatar;
	private String smallAvatar;
	private double longitude;
	private double latitude;
	private Timestamp register;	
	private Date birthday;
	private int grade;
		
	@JsonIgnore
	private Set<GroupUser> groupUsers = new HashSet<GroupUser> ();
	
	public User(){
		this.bigAvatar = "photo\\avatar\\180\\default_big_avatar.png";
		this.smallAvatar = "photo\\avatar\\50\\default_small_avatar.png";
		this.longitude = 0.0;
		this.latitude = 0.0;	
		this.grade = 0;

	}
	
	
	
	public Date getBirthday() {
		return birthday;
	}



	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}



	public int getGrade() {
		return grade;
	}



	public void setGrade(int grade) {
		this.grade = grade;
	}


	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getBigAvatar() {
		return bigAvatar;
	}
	public void setBigAvatar(String bigAvatar) {
		this.bigAvatar = bigAvatar;
	}
	public String getSmallAvatar() {
		return smallAvatar;
	}
	public void setSmallAvatar(String smallAvatar) {
		this.smallAvatar = smallAvatar;
	}
	public Set<GroupUser> getGroupUsers() {
		return groupUsers;
	}
	public void setGroupUsers(Set<GroupUser> groupUsers) {
		this.groupUsers = groupUsers;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public Timestamp getRegisterTime() {
		return register;
	}
	public void setRegisterTime(Timestamp registerTime) {
		this.register = registerTime;
	}
}
