package com.coe.mxcommunity.message.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MessageController {
	@RequestMapping(value="/mymessage")
	public String registerSuccess(HttpSession httpSession, Model model){
		
		model.addAttribute("NAVI_FOCUS", "navi_message");
		return "message/my_message";
	}
}
