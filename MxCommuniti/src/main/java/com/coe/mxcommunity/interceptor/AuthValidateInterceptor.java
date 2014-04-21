package com.coe.mxcommunity.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.coe.mxcommunity.account.entity.User;

public class AuthValidateInterceptor extends HandlerInterceptorAdapter {
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response,
			Object handler) throws Exception{
		HttpSession session = request.getSession();
		User user = (User)request.getSession().getAttribute("GroupUser");
		if (user == null){
			response.sendRedirect("");
			return false;
		}

		return true;
	}
}
