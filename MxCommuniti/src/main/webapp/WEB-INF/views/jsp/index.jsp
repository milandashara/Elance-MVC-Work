<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MXCommunity</title>
<link rel="shortcut icon"href="favicon.ico">
<link rel="stylesheet" type="text/css" href="css/base.css"/>
<link rel="stylesheet" type="text/css" href="css/frame.css"/>
<link rel="stylesheet" type="text/css" href="css/classstyle.css" />
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/bootstrap-responsive.min.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="bg43">
		<div class="f-header">
			<div class="navi-logo">
					<img src="image.png"/>
				</a>
			</div>
		</div>
	</div>
	<div class="index_layout_container">
		<div class="index_sidebar">
			<form class="login_form" name="login" action="login.do" method="post" onsubmit="return login_validate()">
				<div class="erro_box">
					<span id="erro_info" class="erro_info">${message}</span>
				</div>
				<br>
				UserName:
				<br>
				<input class="w186 h32 f14" type="text" id="email" name="email" placeholder="Your Email" value="${email}"/>
				<br>
				Password:
				<br>
				<input class="w186 h32 f14" type="password" id="password" name="password" placeholder="Your Password" 
				       data-toggle="tooltip" title="At least 6 characters for your account safety" data-placement="right value="${password}"/>
				<br>
				<input type="checkbox" id="save_password"/>Remember Password
				<br>
				<input type="submit" id="login_btn" class="btn btn-warning"value="log in" />
			</form>
			<div class="register_panel">
				<input type="button" id="register_btn" class="btn btn-primary" onclick="window.open('register')"/>
			</div>
		</div>
		<div class="index_content">
			<div style="height: 120px;background-repeat: no-repeat;background-position: center; background-image: url(images/tower_adv.png); clear: both; margin:10px 10px 0 20px; border-bottom: 2px solid #f00;"></div>
			<div style="position: relative; float: left; padding-top: 20px; width: 160px;">
			<div style="float: left; width: 600px; padding-top: 20px; min-height: 420px;">
				<div id="speller" style="margin: 0px auto; width: 580px; height: 420px; border: 1px solid #ccc;"></div>
			</div>
		</div>
	</div>
	<div class="f-footer">

		<div class="tc">All Rights Reserved</div>
	</div>

<script src="js/bootstrap.min.js"></script> 	
<script type="text/javascript" src="js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<script type="text/javascript" src="js/index.js"></script>
<script type="text/javascript" src="js/jspeller.js"></script>
<script type="text/javascript" src="js/jspace3d.js"></script>
</body>
</html>