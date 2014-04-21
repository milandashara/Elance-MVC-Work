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
<link rel="shortcut icon"href="favicon.ico">
<title>sharing</title>
<link rel="stylesheet" type="text/css" href="css/base/frame.css"/>
<link rel="stylesheet" type="text/css" href="css/base/home_frame.css" />
<script type="text/javascript" src="js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<script type="text/javascript" src="js/message/my_message.js"></script>
</head>
<body>
<%@include file="../base/fr_header.jsp" %>
<%@include file="feature/my_message_part.jsp" %>

</html>