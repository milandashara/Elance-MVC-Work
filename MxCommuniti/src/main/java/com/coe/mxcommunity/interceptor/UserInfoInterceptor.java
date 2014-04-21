package com.coe.mxcommunity.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.coe.mxcommunity.account.entity.User;

public class UserInfoInterceptor  extends HandlerInterceptorAdapter{
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response,
			Object handler,
			ModelAndView modelAndView) throws Exception{
		if (modelAndView != null){
			User user = (User)request.getSession().getAttribute("GroupUser");
			if (user != null){
				modelAndView.addObject("username", user.getName());
			}	
		}
	}
}
