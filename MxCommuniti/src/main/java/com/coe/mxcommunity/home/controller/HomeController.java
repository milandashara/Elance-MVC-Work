package com.coe.mxcommunity.home.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coe.mxcommunity.account.entity.User;

@Controller
public class HomeController {
	@RequestMapping(value="/home")
	public String home(HttpSession httpSession, Model model)
	{	
		User currentUser = (User)httpSession.getAttribute("GroupUser");
		model.addAttribute("USER_LONGITUDE", currentUser.getLongitude());
		model.addAttribute("USER__LATITUDE", currentUser.getLatitude());
		model.addAttribute("USER_CODE", currentUser.getCode());
		
		model.addAttribute("NAVI_FOCUS", "navi_map");
		return "home/home";
	}
}
