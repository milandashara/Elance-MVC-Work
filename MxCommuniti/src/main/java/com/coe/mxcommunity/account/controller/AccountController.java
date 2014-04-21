package com.coe.mxcommunity.account.controller;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.coe.mxcommunity.account.dao.UserDao;
import com.coe.mxcommunity.account.entity.User;
import com.coe.mxcommunity.account.service.UserService;
import com.coe.mxcommunity.help.CodeGenerator;
import com.coe.mxcommunity.help.entity.UserType;
import com.coe.mxcommunity.message.service.MessageService;

@Controller
public class AccountController {
	
	@RequestMapping(value="/index")
	public String index(HttpSession httpSession, Model model)
	{
		User currentUser = (User)httpSession.getAttribute("GroupUser");
		if (currentUser != null){
			return "redirect:/home";
		}
		
		String loginErr = (String)httpSession.getAttribute("login_error");
		String email = (String)httpSession.getAttribute("login_email");
		String password = (String)httpSession.getAttribute("login_password");
		model.addAttribute("message", loginErr);
		model.addAttribute("email", email);
		model.addAttribute("password", password);		
		httpSession.removeAttribute("login_error");
		httpSession.removeAttribute("login_email");
		httpSession.removeAttribute("login_password");
		
		return "index";
	}
	
	@RequestMapping(value="/logout.do")
	public String logoutDo(HttpSession httpSession, Model model){
		httpSession.removeAttribute("GroupUser");
		return "redirect:index";
	}
	
	@RequestMapping(value="/login.do", method=RequestMethod.POST)
	public String loginDo(HttpSession httpSession, 
			@RequestParam("email") String email, 
			@RequestParam("password") String password, 
			Model model){
		httpSession.removeAttribute("GroupUser");
		User user = UserDao.getUserByEmail(email.toLowerCase());
		if (user != null && user.getPassword().equals(password)){
			UserService.updateUser(user);
			
			httpSession.setAttribute("GroupUser", user);
			return "redirect:/home";
		}
		else {
			httpSession.setAttribute("login_email", email);
			httpSession.setAttribute("login_password", password);	
			httpSession.setAttribute("login_error", "Username or password is not correct!");
			return "redirect:/";
		}	
	}
	
	@RequestMapping(value="/register")
	public String register(HttpSession httpSession, Model model){
		String email_error = (String)httpSession.getAttribute("register_email_error");
		model.addAttribute("email_error", email_error);
		httpSession.removeAttribute("register_email_error");
		return "account/register";
	}
	
	@RequestMapping(value="/registerSuccess")
	public String registerSuccess(HttpSession httpSession, Model model){
		return "account/register_success";
	}
	
	@RequestMapping(value="/register.do")
	public String registerDo(HttpSession httpSession, @ModelAttribute User user, Model model){	
		if (UserDao.emailExist(user.getEmail()))
		{
			httpSession.setAttribute("register_email_error", "This Email has been registered!");
			return "forward:/register";
		}
		
		user.setCode(CodeGenerator.generateUserCode(UserType.MALE));
		user.setRegisterTime(new Timestamp((new Date()).getTime()));
		UserDao.addUser(user);
		httpSession.setAttribute("GroupUser", user);
		model.addAttribute("usename", user.getName());
		
		if (user != null){
			MessageService.sendMessageToUser(user.getId(), user.getName() + ", Welcome to the MxCommunity!");
		}
		
		return "account/register_success";
	}
	
	@RequestMapping(value="/profile")
	public String profile(HttpSession httpSession, Model model){

		return "account/profile";
	}
}
