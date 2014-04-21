<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<body>
	<div class="fr_navi">
		<div class="navi_fix">
			<div class="navi_wrapper">
				<div class="navi_logo">
				</div>
				<div class="navi_navi">
					<div class="navi_setting">
						<img src="images.png" style="height: 28px; margin-top: 6px;"/>
					</div>
					<div class="navi_setting">
						<a class="setting_name" href="home">Home</a>
					</div>		
				</div>
				<div class="navi_person">
					<div class="navi_setting">
						<a class="setting_name" href="profile">${username}</a>
					</div>
					<div class="navi_setting">
						<a class="setting_name" href="logout.do">Quit</a>
					</div>
				</div>
				<div class="navi_search">
					<input id="navSearchInput" value="Search"></input>
					<a href="search"></a>
				</div>
			</div>
		</div>
	</div>
	<div class="fr_main">
		<div class="main_wrapper">