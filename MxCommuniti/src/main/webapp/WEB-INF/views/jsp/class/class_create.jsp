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
<title>Create class</title>
<link rel="stylesheet" type="text/css" href="css/base/frame.css"/>
<link rel="stylesheet" type="text/css" href="css/base/home_frame.css" />
<link rel="stylesheet" type="text/css" href="css/base/dialog.css" />
<link rel="stylesheet" type="text/css" href="css/themes/smoothness/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="css/pages/class/create.css" />
<script type="text/javascript" src="js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/base/base.js"></script>
<script type="text/javascript" src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/class/create.js"></script>
<script type="text/javascript" src="js/dlg/school-dlg.js"></script>
<script type="text/javascript" src="js/util.js"></script>
</head>
<body>
<%@include file="../base/fr_header_s.jsp" %>
<%@include file="../class/feature/create_class.jsp" %>

</html>