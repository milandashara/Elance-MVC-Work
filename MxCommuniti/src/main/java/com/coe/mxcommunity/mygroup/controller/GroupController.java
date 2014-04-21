package com.coe.mxcommunity.mygroup.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.coe.mxcommunity.account.entity.User;
import com.coe.mxcommunity.mygroup.entity.MyGroup;
import com.coe.mxcommunity.mygroup.service.MyGroupService;

@Controller
public class GroupController {
	@RequestMapping(value="/group/{groupCode}", method=RequestMethod.GET)
	public String mygroup(HttpSession httpSession, @PathVariable int groupCode, Model model){
		MyGroup myGroup = MyGroupService.getGroupByCode(groupCode);
		User currentUser = (User)httpSession.getAttribute("GroupUser");
		
		model.addAttribute("GROUP_ID", myGroup.getId());
		model.addAttribute("GROUP_CODE", myGroup.getCode());
		model.addAttribute("GROUP_NAME", myGroup.getName());
		model.addAttribute("USER_CODE", currentUser.getCode());
		
		model.addAttribute("NAVI_FOCUS", "navi_group_" + groupCode);
		
		return "group/group_home";
	}
	
	@RequestMapping(value="/group/demo/{groupCode}", method=RequestMethod.GET)
	public String demoGroup(HttpSession httpSession, @PathVariable int groupCode, Model model){
		User currentUser = (User)httpSession.getAttribute("GroupUser");
		
		model.addAttribute("GROUP_ID", "999999999");
		model.addAttribute("GROUP_CODE", "999999999");
		model.addAttribute("GROUP_NAME", "Basketball Club");
		model.addAttribute("USER_CODE", currentUser.getCode());
		
		model.addAttribute("NAVI_FOCUS", "navi_group_demo_1");
		
		return "group/group_home_demo";
	}
	
	@RequestMapping(value="/group/album/{groupId}", method=RequestMethod.GET)
	public String groupInfo(@PathVariable Long groupId, Model model){	
		model.addAttribute("GROUP_ID", groupId);
		
		MyGroup myGroup = MyGroupService.getGroupById(groupId);
		
		model.addAttribute("GROUP_CODE", myGroup.getCode());
		return "group/group_album";
	}
	
	@RequestMapping(value="/group/create", method=RequestMethod.GET)
	public String create(Model model){
		return "group/group_create";
	}
	
	@RequestMapping(value="/group/create.do")
	public String createDo(
			@RequestParam("schoolType") int schoolType,
			@RequestParam("schoolCode") String schoolCode,
			@RequestParam("schoolName") String schoolName,
			@RequestParam("schoolYear") int schoolYear,	
			@RequestParam("groupName") String groupName,
			HttpSession httpSession,
			Model model)
	{
		if (MyGroupService.isGroupExit(groupName)){
			model.addAttribute("create_group_error", "The Group has already existed");
			return "group/create";
		}
		else{
			MyGroup myGroup = new MyGroup();
			myGroup.setName(groupName);
			myGroup.setSchoolCode(schoolCode);
			myGroup.setSchoolName(schoolName);
			myGroup.setGroupType(schoolType);
			myGroup.setSchoolYear(schoolYear);
			
			User currentUser = (User)httpSession.getAttribute("GroupUser");
			MyGroupService.createGroup(myGroup, currentUser);
			
			model.addAttribute("GROUP_ID", myGroup.getId());
			model.addAttribute("GROUP_CODE", myGroup.getCode());
			model.addAttribute("GROUP_NAME", myGroup.getName());
			model.addAttribute("USER_CODE", currentUser.getCode());
			
			model.addAttribute("NAVI_FOCUS", "navi_group_" + myGroup.getCode());
			
			return "group/group_home";
		}
	}
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public String search(Model model){
		return "search/search";
	}
}
