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
<link rel="stylesheet" type="text/css" href="css/pages/class/class_home.css" />
<link rel="stylesheet" type="text/css" href="js/JAlbum/theme/jalbum.css" />
<script type="text/javascript" src="js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<script type="text/javascript" src="js/JAlbum/jalbum.js"></script>
<script type="text/javascript" src="js/base/base.js"></script>
<script type="text/javascript" src="js/class/class_info.js"></script>
<link rel="stylesheet" type="text/css" href="js/JUpload/theme/jupload.css" />
<script type="text/javascript" src="js/jquery/ajaxfileupload.js"></script>
<script type="text/javascript" src="js/JUpload/jupload.js"></script>
<script type="text/javascript">
	var g_class_id = ${CLASS_ID};
</script>
</head>
<body>
<%@include file="../base/fr_header_s.jsp" %>
<%@include file="feature/class_album_part.jsp" %>

</html>