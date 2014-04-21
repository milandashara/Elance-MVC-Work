<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.coe.mxcommunity.account.entity.User"%>
<% User currentUser = (User)session.getAttribute("GroupUser"); %>

<div class="box-wrapper">
	<div class="account-profile-panel">
		<div class="top-border"></div>
		<div class="account-profile-box">
			<ul class="account-profile-tabs">
				<li id="info_tab" class="focus" onclick="setTab('info')">Personal Information</li>
				<li id="avatar_tab" onclick="setTab('avatar')">My Photo</li>
				<li id="password_tab" onclick="setTab('password')">Account Security</li>
			</ul>
			<div class="tab-content" id="info_content">
				<form action="updateUserInfo" style="padding: 50px 0 0 50px;" method="post" onsubmit="return validate()" target="aa">
					<table class="g_common_table">
						<tr>
							<td class="right">
								<span class="req_mark">*</span>Your Name：
							</td>
							<td>
								<input class="inputbox" id="name" name="name" onchange="checkNa()" type="text" value="<%= currentUser.getName()%>"/>
							</td>
							<td>
								<span class="input_err" id="name_err"></span>
							</td>
						</tr>
						<tr>
							<td class="right">
								<span class="req_mark">*</span>Gender：
							</td>
							<td>
								<input type="radio" <% if (currentUser.getSex() == 1) {%> checked="checked"<%} %> name="sex" value="1"/>
								<span class="textbox">Male</span>
								<input type="radio" <% if (currentUser.getSex() == 2) {%> checked="checked"<%} %> name="sex" value="2"/>
								<span class="textbox">Female</span>
							</td>
						</tr>
							<tr>
							<td class="right">
							</td>
							<td>
								<input  type="submit" style="width: 120px; height: 48px;" value="确认" />
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="tab-content" id="avatar_content">
				<div style="padding: 80px 30px 30px 80px; height: 300px; margin: auto; ">
					<div style="float: left">
						<div>
							<img id="avatar_180" style="width: 180px; height: 180px" src="<%=currentUser.getBigAvatar()%>"></img>
						</div>
						<div style="text-align: center;">
							180 X 180
						</div>
					</div>
					<div style="float: left; margin: 0 30px;">
						<div>
							<img id="avatar_50" style="width: 50px; height: 50px" src="<%=currentUser.getSmallAvatar()%>"></img>
						</div>
						<div style="text-align: center;">
							50 X 50
						</div>
					</div>
					<div style="clear: both">
						<form action="upload" method="post" enctype="multipart/form-data" target="aa">  
            				<input type="file" name="file" style="height: 32px;" />
            				<input type="submit" value="上传" style="width: 100px; height: 32px;"/>
						</form> 
					</div>
				</div>
			</div>
			<div class="tab-content" id="password_content">
				<div style="padding: 50px 0 0 50px;">
					<table class="g_common_table">
						<tr>
							<td class="right">
								<span class="req_mark">*</span>old password：
							</td>
							<td>
								<input class="inputbox" id="oldPassword" name="oldPassword" onchange="checkOldPs()" type="password"/>
							</td>
							<td>
								<span class="input_err" id="oldpPassword_err"></span>
							</td>
						</tr>
						<tr>
							<td class="right">
								<span class="req_mark">*</span>new password：
							</td>
							<td>
								<input class="inputbox" id="newPassword" name="newPassword" onchange="checkNewPs()" type="password"/>
							</td>
							<td>
								<span class="input_err" id="newPassword_err"></span>
							</td>
						</tr>
						<tr>
							<td class="right">
								<span class="req_mark">*</span>new password again：
							</td>
							<td>
								<input class="inputbox" id="confirmPassword" name="confirmPassword" onchange="checkConfirmPs()" type="password"/>
							</td>
							<td>
								<span class="input_err" id="confirmPassword_err"></span>
							</td>
						</tr>
						</tr>
							<tr>
							<td class="right">
							</td>
							<td>
								<input  type="submit" onclick="changePassword()" style="width: 120px; height: 48px;" value="sure" />
							</td>
						</tr>
					</table>
				</div>
			</div>
			<iframe name="aa" src="" style="display:none"></iframe>
		</div>
	</div>
</div>