package com.coe.mxcommunity.message.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coe.mxcommunity.account.entity.User;
import com.coe.mxcommunity.ajax.AjaxResult;
import com.coe.mxcommunity.message.entity.Message;
import com.coe.mxcommunity.message.service.MessageService;

@Controller
public class MessageAjaxController {
	@RequestMapping(value="/ajax/message/publish", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResult publishMessage(@RequestBody MessageRequest msgReq, HttpSession httpSession)
	{
		AjaxResult result = new AjaxResult();
		
		User currentUser = (User)httpSession.getAttribute("GroupUser");
		
		Message msg = MessageService.publishUserMessage(msgReq, currentUser.getId());
	
		result.setData(msg);
		result.setStatus(true);
		
		return result;
	}
    
	@RequestMapping(value="/ajax/message/getMessages", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResult getMessages(
			@RequestParam("groupId") Long groupId,
			@RequestParam("startTime") long startTime,
			@RequestParam("pageNumber") int pageNumber,
			@RequestParam("pageSize") int pageSize)
	{
		AjaxResult result = new AjaxResult();
		List<Message> msgList = MessageService.getMessageList(groupId, new Date(startTime), pageNumber, pageSize);
		result.setStatus(true);
		result.setData(msgList);
		
		return result;
	}
	
	@RequestMapping(value="/ajax/message/messagebox", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResult getMessageBox(
			@RequestParam("startTime") long startTime,
			@RequestParam("pageNumber") int pageNumber,
			@RequestParam("pageSize") int pageSize,
			HttpSession httpSession)
	{
		AjaxResult result = new AjaxResult();
		
		User currentUser = (User)httpSession.getAttribute("GroupUser");
		List<Message> msgList = MessageService.getMyMessage(currentUser.getId(), new Date(startTime), pageNumber, pageSize);
		result.setStatus(true);
		result.setData(msgList);
		
		return result;
	}
}
