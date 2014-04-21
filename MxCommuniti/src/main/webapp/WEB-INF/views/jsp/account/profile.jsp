<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 %>
<html>
<head>
<base href="<%=basePath %>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Your life in MXCommunity</title>
<link rel="shortcut icon"href="favicon.ico">
<link rel="stylesheet" type="text/css" href="css/base/frame.css" />
<link rel="stylesheet" type="text/css" href="css/base/home_frame.css" />
<link rel="stylesheet" type="text/css" href="css/pages/account/profile.css" />
<script type="text/javascript" src="js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/base/base.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<script type="text/javascript" src="js/account/profile.js"></script>
</head>
<%@include file="../base/fr_header_s.jsp" %>
<%@include file="feature/profile_part.jsp" %>

</html>