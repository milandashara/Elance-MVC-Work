package com.coe.mxcommunity.mygroup.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.coe.mxcommunity.account.dao.UserDao;
import com.coe.mxcommunity.account.entity.User;
import com.coe.mxcommunity.account.service.UserService;
import com.coe.mxcommunity.help.CodeGenerator;
import com.coe.mxcommunity.help.entity.GroupType;
import com.coe.mxcommunity.message.service.MessageService;
import com.coe.mxcommunity.mygroup.dao.MyGroupDao;
import com.coe.mxcommunity.mygroup.entity.MyGroup;
import com.coe.mxcommunity.view.entity.Pager;

public class MyGroupService {
	public static Pager getGroupByQuery(int pageNum, int pageSize, String groupName, int groupType, String schoolCode, int schoolYear){
		
		MyGroup myGroup = new MyGroup();
		if (groupName != null && groupName.length() > 0){
			myGroup.setName("%" + groupName + "%" );
		}
		
		if (groupType > 0){
			myGroup.setGroupType(groupType);
		}
		
		if (schoolCode != null && schoolCode.length() > 0){
			myGroup.setSchoolCode(schoolCode);
		}
		
		if (schoolYear > 0){
			myGroup.setSchoolYear(schoolYear);
		}
		
		Pager pager = MyGroupDao.getGroupListByExample(pageNum, pageSize, myGroup);
		
		
		return pager;
	}
	
	public static boolean isGroupExit(String groupName){
		boolean bRet = false;
		bRet = MyGroupDao.isGroupExist(groupName);
		return bRet;
	}
	
//	public static List<Desk> createDeskList(int columNum, int rowNum){
//		List<Desk> deskList = new ArrayList<Desk>();
//		
//		Desk desk = null;
//		int roomWidth = 558;
//		int borderWidth = 60;
//		int deskWidth = 45;
//		int yCon = 60;
//		int xCon = (roomWidth - borderWidth * 2 - deskWidth * 2) / (columNum - 1);
//		for (int i = 0; i < rowNum; i++){
//			for (int j = 0; j < columNum; j++){
//				desk = new Desk();
//				desk.setX(j * xCon + borderWidth);
//				desk.setY(i * yCon + 150);
//				deskList.add(desk);
//
//				desk = new Desk();
//				desk.setX(j * xCon + borderWidth + deskWidth);
//				desk.setY(i * yCon + 150);
//				deskList.add(desk);
//			}
//		}
//		
//		return deskList;
//	}
	
	public static MyGroup createGroup(MyGroup myGroup, User creator){
		MyGroup rGroup = null;
		
		int schoolType = myGroup.getGroupType();
		GroupType groupType = GroupType.NONE;
		if (schoolType == 1){
			groupType = GroupType.TRAVEL;
		}
		else if (schoolType == 2){
			groupType = GroupType.FOOD;	
		}
		else if(schoolType == 3){
			groupType = GroupType.MUSIC;					
		}
		else if(schoolType == 4){
			groupType = GroupType.SPORT;					
		}
		else if (schoolType == 9){
			groupType = GroupType.MODEL;
		}
		
		int code;
		code = CodeGenerator.generateGroupCode(groupType, myGroup.getSchoolYear());		
		myGroup.setCode(code);
		myGroup.setCreateTime(new Timestamp((new Date()).getTime()));
		
		myGroup.setCreator(creator.getCode());
		
//		Desk desk = null;
//		int xCon = 558 / 7;
//		int yCon = 60;
//		int tempCon = 0;
//		for (int i = 0; i < 40; i++){
//			desk = new Desk();
//			
//			if (i % 2 != 0){
//				tempCon += 54;
//			}else{
//				tempCon = ((i % 6) + 1) * xCon;
//			}
//			desk.setX(tempCon);
//			
//			
//			desk.setY((i / 6) * yCon + 150);
//			myGroup.getDesks().add(desk);
//			desk.setMyGroup(myGroup);
//		}
//		List<Desk> deskList = MyGroupService.createDeskList(4, 5);
//		for (int i = 0; i < deskList.size(); i++){
//			myGroup.getDesks().add(deskList.get(i));
//			deskList.get(i).setMyGroup(myGroup);
//		}
		
		rGroup = MyGroupDao.createGroup(myGroup, creator);
		if (rGroup != null){
			MessageService.sendMessageToGroup(rGroup.getId(), rGroup.getName() + "The Group has been created, welcome to here!");
		}
		
		return rGroup;
	}
	
	public static boolean joinToGroup(Long groupId, Long userId, boolean isPending){
		boolean bRet = false;
		
		bRet = MyGroupDao.addUserToGroup(groupId, userId, isPending);
		if (bRet && !isPending){		
			User user = UserDao.getUser(userId);
			if (user != null){
				MessageService.sendMessageToGroup(groupId, user.getName() + "Join this group");
			}
		}
		
		return bRet;
	}
	
	public static boolean addUserToGroup(int groupCode, User user, boolean isPending){
		boolean bRet = false;
		
		MyGroup myGroup = new MyGroup();
		myGroup.setCode(groupCode);
		bRet = MyGroupDao.addUserToGroup(myGroup, user, isPending);
		
		return bRet;
	}
		
	public static MyGroup getGroupById(Long groupId){
		return MyGroupDao.getGroupById(groupId);
	}
	
	public static MyGroup getGroupByCode(int groupCode){
		return MyGroupDao.getGroupByCode(groupCode);
	}
	
	public static List<User> getUserListByGroupCode(int groupCode, boolean isPending){
		return MyGroupDao.getUserListByGroupCode(groupCode, isPending);
	}
	
	public static List<MyGroup> getGroupListByUser(User user, boolean isPending){
		return MyGroupDao.getGroupListByUserCode(user.getCode(), isPending);
	}
	
	public static List<MyGroup> getGroupListByUserCode(int userCode, boolean isPending){
		return MyGroupDao.getGroupListByUserCode(userCode, isPending);
	}
	
	public static boolean approvePending(Long groupId, Long userId){
		boolean bRet = false;
		bRet = MyGroupDao.approvePending(groupId, userId);
		if (bRet){
			User user = UserDao.getUser(userId);
			if (user != null){
				MessageService.sendMessageToGroup(groupId, user.getName() + "Join This Group");
			}
		}
		
		return bRet;
	}
	
	public static boolean refusePending(int groupCode, int userCode){
		return MyGroupDao.refusePending(groupCode, userCode);
	}
}
