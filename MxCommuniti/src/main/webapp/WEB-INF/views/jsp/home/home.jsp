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
<link rel="stylesheet" type="text/css" href="css/pages/home/home.css" />
<script type="text/javascript" src="js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/base/base.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<script type="text/javascript" src="js/home/home.js"></script>
<script type="text/javascript" src="js/openlayers/OpenLayers.js"></script>
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false&region=US"></script>
<script type="text/javascript" src="js/JMap/theme/jmap.css"></script>
<script type="text/javascript" src="js/JMap/jmap.js"></script>
<script type="text/javascript">
	var g_user_code = ${USER_CODE};
	var g_user_longitude = ${USER_LONGITUDE};
	var g_user_latitude = ${USER__LATITUDE};
</script>
</head>
<%@include file="../base/fr_header.jsp" %>
<%@include file="feature/home_part.jsp" %>

</html>