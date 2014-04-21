package com.coe.mxcommunity.account.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.coe.mxcommunity.account.dao.UserDao;
import com.coe.mxcommunity.account.entity.User;
import com.coe.mxcommunity.account.service.UserService;
import com.coe.mxcommunity.ajax.AjaxResult;
import com.coe.mxcommunity.mygroup.entity.MyGroup;
import com.coe.mxcommunity.mygroup.service.MyGroupService;

@Controller
public class AccountAjaxController {
	@RequestMapping(value="/ajax/verify", method=RequestMethod.GET)
	@ResponseBody
	public AjaxResult verify(String email)
	{
		AjaxResult ret = new AjaxResult();
		if (UserDao.emailExist(email.toLowerCase())){
			ret.setStatus(false);
			ret.setError("Sorry,Email has been taken!");
		}else{
			ret.setStatus(true);
		}
		
		return ret;
	}
	
	@RequestMapping(value="/ajax/user/getInfo", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResult getInfo(HttpSession httpSession)
	{
		AjaxResult result = new AjaxResult();
		
		User currentUser = (User)httpSession.getAttribute("GroupUser");
		
		result.setStatus(true);
		result.setData(currentUser);
		
		return result;
	}
	
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String handleFormUpload(@RequestParam("file") MultipartFile file, HttpSession httpSession, Model model)  {

		User currentUser = (User)httpSession.getAttribute("GroupUser");
        if (!file.isEmpty()) {
        	try{ 		
        		UserService.updateUserAvatar(file, currentUser);
        	}
        	catch (Exception e){     		
        	}
       }
        
        return "account/feature/upload_result_part";
    }
	@RequestMapping(value="/ajax/user/getUserGroup", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResult getUserGroup(HttpSession httpSession)
	{
		AjaxResult result = new AjaxResult();
		
		User currentUser = (User)httpSession.getAttribute("GroupUser");
		List<MyGroup> myGroupList = MyGroupService.getGroupListByUser(currentUser, false);
		
		result.setStatus(true);
		result.setData(myGroupList);
		
		return result;
	}
	
	@RequestMapping(value="/ajax/user/getUserPendingGroup", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResult getUserPendingGroup(HttpSession httpSession)
	{
		AjaxResult result = new AjaxResult();
		
		User currentUser = (User)httpSession.getAttribute("GroupUser");
		List<MyGroup> myGroupList = MyGroupService.getGroupListByUser(currentUser, true);
		
		result.setStatus(true);
		result.setData(myGroupList);
		
		return result;
	}
    
	@RequestMapping(value="/ajax/user/updateUserPosition", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResult updateUserPosition(double longitude, double latitude, HttpSession httpSession)
	{
		AjaxResult result = new AjaxResult();
		
		User currentUser = (User)httpSession.getAttribute("GroupUser");
		currentUser.setLongitude(longitude);
		currentUser.setLatitude(latitude);
		UserService.updateUser(currentUser);
		
		result.setData(currentUser);
		result.setStatus(true);
		
		return result;
	}
	
	@RequestMapping(value="updateUserInfo", method=RequestMethod.POST)
	public String updateUserInfo(String name, int sex, HttpSession httpSession)
	{		
		User currentUser = (User)httpSession.getAttribute("GroupUser");
		currentUser.setName(name);
		currentUser.setSex(sex);
		UserService.updateUser(currentUser);
		
        return "account/feature/updateUserInfo_result_part";
	}
	
	@RequestMapping(value="/ajax/user/updatePassword", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResult updatePassword(String oldPassword, String newPassword, HttpSession httpSession)
	{
		AjaxResult result = new AjaxResult();
		
		User currentUser = (User)httpSession.getAttribute("GroupUser");
		if (currentUser.getPassword().equals(oldPassword)){
			currentUser.setPassword(newPassword);
			UserService.updateUser(currentUser);
			
			result.setStatus(true);
		}else{
			result.setStatus(false);
			result.setData("Old password required, please try again!");
		}
		
        return result;
	}
}
