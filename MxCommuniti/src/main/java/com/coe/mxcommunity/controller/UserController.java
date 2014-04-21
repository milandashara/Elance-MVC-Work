package com.coe.mxcommunity.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.coe.mxcommunity.account.dao.UserDao;
import com.coe.mxcommunity.account.entity.User;

@Controller
public class UserController {
	
	@RequestMapping(value="/hello", method=RequestMethod.GET)
	public String sayHello(Model model)
	{
		model.addAttribute("message", "Michael, welcome!");
		return "hello";
	}
}
