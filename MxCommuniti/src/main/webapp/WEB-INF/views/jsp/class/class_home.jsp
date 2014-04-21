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
<title>MXCommunity</title>
<link rel="stylesheet" type="text/css" href="css/base/frame.css" />
<link rel="stylesheet" type="text/css" href="css/base/home_frame.css" />
<link rel="stylesheet" type="text/css" href="css/pages/class/class_home.css" />
<script type="text/javascript" src="js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<script type="text/javascript" src="js/base/base.js"></script>
<script type="text/javascript" src="js/class/class_home.js"></script>
<link rel="stylesheet" type="text/css" href="js/JClass/theme/jclass.css" />
<script type="text/javascript" src="js/JClass/jclass.js"></script>
<script type="text/javascript" src="js/jquery.json-2.4.js"></script>
<link rel="stylesheet" type="text/css" href="js/JUpload/theme/jupload.css" />
<script type="text/javascript" src="js/jquery/ajaxfileupload.js"></script>
<script type="text/javascript" src="js/JUpload/jupload.js"></script>
<link type="text/css" rel="stylesheet" href="js/lightbox/jquery.lightbox-0.5.css" />
<script type="text/javascript" src="js/lightbox/lightbox.min.js"></script>
<script type="text/javascript">
	var g_class_id = ${CLASS_ID};
	var g_class_code = ${CLASS_CODE};
	var g_class_name = "${CLASS_NAME}";
	var g_user_code = ${USER_CODE};
</script>
</head>
<%@include file="../base/fr_header.jsp" %>
<%@include file="feature/class_home_part.jsp" %>

</html>