package com.coe.mxcommunity.mygroup.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;

public class MyGroup {
	private Long id;
	private int code;
	private String name;
	private int groupType;
	private String schoolCode;
	private String schoolName;
	private int schoolYear;
	private int totalNum;
	private int actualNum;
	private int creator;
	private Timestamp createTime;
	
	@JsonIgnore
	private Set<GroupUser> groupUsers = new HashSet<GroupUser>();
	
//	@JsonIgnore
//	private Set<Desk> desks = new HashSet<Desk>();
//	
//	public Set<Desk> getDesks() {
//		return desks;
//	}
//
//	public void setDesks(Set<Desk> desks) {
//		this.desks = desks;
//	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public Set<GroupUser> getGroupUsers() {
		return groupUsers;
	}

	public void setGroupUsers(Set<GroupUser> groupUsers) {
		this.groupUsers = groupUsers;
	}

	public MyGroup(){
		
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
	public int getGroupType() {
		return groupType;
	}
	public void setGroupType(int groupType) {
		this.groupType = groupType;
	}
	public int getSchoolYear() {
		return schoolYear;
	}
	public void setSchoolYear(int schoolYear) {
		this.schoolYear = schoolYear;
	}
	public String getSchoolCode() {
		return schoolCode;
	}
	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public int getActualNum() {
 		return actualNum;
	}
	public void setActualNum(int actualNum) {
		this.actualNum = actualNum;
	}
	public int getCreator(){
		return creator;
	}
	public void setCreator(int creator){
		this.creator = creator;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
}
