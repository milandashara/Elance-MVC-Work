package com.coe.mxcommunity.mygroup.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coe.mxcommunity.account.entity.User;
import com.coe.mxcommunity.ajax.AjaxResult;
import com.coe.mxcommunity.mygroup.service.MyGroupService;
import com.coe.mxcommunity.view.entity.Pager;

@Controller
public class MyGroupAjaxController {	
	@RequestMapping(value="/ajax/group/grouproom", method=RequestMethod.GET)
	public String grouproom(Model model)
	{	
		return "group/feature/grouproom";
	}
	
	@RequestMapping(value="/ajax/group/members", method=RequestMethod.GET)
	public String members(Model model)
	{	
		return "group/feature/members";
	}
	
	@RequestMapping(value="/ajax/group/ranking", method=RequestMethod.GET)
	public String ranking(Model model)
	{	
		return "group/feature/ranking";
	}
	
	@RequestMapping(value="/ajax/group/study", method=RequestMethod.GET)
	public String study(Model model)
	{	
		return "group/feature/study";
	}
	
	@RequestMapping(value="/ajax/group/search", method=RequestMethod.POST)
	@ResponseBody
	public Pager searchGroup(
			@RequestParam("groupName") String groupName,
			@RequestParam("schoolType") int schoolType,
			@RequestParam("schoolCode") String schoolCode,
			@RequestParam("schoolYear") int schoolYear,
			@RequestParam("pageNumber") int pageNumber,
			@RequestParam("pageSize") int pageSize)
	{
		Pager pager = MyGroupService.getGroupByQuery(pageNumber, pageSize, groupName, schoolType, schoolCode, schoolYear);
		return pager;
	}
	
	@RequestMapping(value="/ajax/group/join", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResult joinToGroup(@RequestParam("groupId") Long groupId, HttpSession httpSession)
	{
		AjaxResult result = new AjaxResult();
		
		User currentUser = (User)httpSession.getAttribute("GroupUser");	
		if (MyGroupService.joinToGroup(groupId, currentUser.getId(), true)){
			result.setData(groupId);
			result.setStatus(true);
		}
		else{
			result.setStatus(false);
		}
		
		return result;
	}
	
//	@RequestMapping(value="/ajax/group/addDesk", method=RequestMethod.POST)
//	@ResponseBody
//	public AjaxResult addDesk(
//			@RequestParam("groupCode") int groupCode,
//			@RequestParam("x") int x,
//			@RequestParam("y") int y,
//			HttpSession httpSession)
//	{
//		AjaxResult result = new AjaxResult();
//		
//		Desk desk = new Desk();
//		desk.setX(x);
//		desk.setY(y);
//		desk = MyGroupService.addDeskToGroup(groupCode, desk);
//		if (desk != null){
//			result.setStatus(true);
//			desk.setMyGroup(null);
//			result.setData(desk);
//		}else{
//			result.setStatus(false);
//		}
//		
//		return result;
//	}
//	
//	@RequestMapping(value="/ajax/group/deleteDesk", method=RequestMethod.POST)
//	@ResponseBody
//	public AjaxResult deleteDesk(
//			@RequestParam("deskId") Long deskId,
//			HttpSession httpSession)
//	{
//		AjaxResult result = new AjaxResult();
//		
//		boolean bRet = MyGroupService.deleteDesk(deskId);
//		if (bRet){
//			result.setStatus(true);
//		}else{
//			result.setStatus(false);
//		}
//		
//		return result;
//	}
//	
//	@RequestMapping(value="/ajax/group/updateDeskPositions", method=RequestMethod.POST)
//	@ResponseBody
//	public AjaxResult updateDeskPositions(
//			@RequestBody List<Desk> desks,
//			HttpSession httpSession)
//	{
//		AjaxResult result = new AjaxResult();
//		
//		boolean bRet = MyGroupService.updateDeskPositions(desks);
//		if (bRet){
//			result.setStatus(true);
//		}else{
//			result.setStatus(false);
//		}
//		
//		return result;
//	}
//	
//	@RequestMapping(value="/ajax/group/bindDeskUser", method=RequestMethod.POST)
//	@ResponseBody
//	public AjaxResult bindDeskUser(
//			@RequestParam("deskId") Long deskId,
//			HttpSession httpSession)
//	{
//		AjaxResult result = new AjaxResult();
//		
//		User currentUser = (User)httpSession.getAttribute("GroupUser");
//		Desk desk = MyGroupService.bindDeskUser(deskId, currentUser);
//		if (desk != null){
//			result.setStatus(true);
//			result.setData(desk);
//		}else{
//			result.setStatus(false);
//		}
//		
//		return result;
//	}
//	
//	@RequestMapping(value="/ajax/group/getDesks", method=RequestMethod.POST)
//	@ResponseBody
//	public AjaxResult getDesks(
//			@RequestParam("groupCode") int groupCode,
//			HttpSession httpSession)
//	{
//		AjaxResult result = new AjaxResult();
//		
//		List<Desk> deskList = MyGroupService.getDeskList(groupCode);
//		result.setStatus(true);
//		result.setData(deskList);
//		
//		return result;
//	}
	
	@RequestMapping(value="/ajax/group/getGroupMembers", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResult getGroupMembers(
			@RequestParam("groupCode") int groupCode,
			HttpSession httpSession)
	{
		AjaxResult result = new AjaxResult();
		
		List<User> userList = MyGroupService.getUserListByGroupCode(groupCode, false);
		result.setStatus(true);
		result.setData(userList);
		
		return result;
	}
	
	@RequestMapping(value="/ajax/group/getGroupPendingMembers", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResult getGroupPendingMembers(
			@RequestParam("groupCode") int groupCode,
			HttpSession httpSession)
	{
		AjaxResult result = new AjaxResult();
		
		List<User> userList = MyGroupService.getUserListByGroupCode(groupCode, true);
		result.setStatus(true);
		result.setData(userList);
		
		return result;
	}
	
	@RequestMapping(value="/ajax/group/approvePending", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResult approvePending(
			@RequestParam("groupId") Long groupId,
			@RequestParam("userId") Long userId,
			HttpSession httpSession)
	{
		AjaxResult result = new AjaxResult();
		
		MyGroupService.approvePending(groupId, userId);
		result.setStatus(true);
		
		return result;
	}
	
	@RequestMapping(value="/ajax/group/refusePending", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResult refusePending(
			@RequestParam("groupCode") int groupCode,
			@RequestParam("memberCode") int memberCode,
			HttpSession httpSession)
	{
		AjaxResult result = new AjaxResult();
		
		MyGroupService.refusePending(groupCode, memberCode);
		result.setStatus(true);
		
		return result;
	}
}
