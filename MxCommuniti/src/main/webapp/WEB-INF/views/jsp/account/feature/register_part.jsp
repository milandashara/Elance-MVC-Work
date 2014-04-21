<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="main_tab">
	<span class="tab_text">Registration</span>
</div>
<div class="main_content">
	<form class="reg_form" action="register.do" method="post" onsubmit="return validate()">
		<table>
			<tr>
				<td class="right">
					<span class="req_mark">*</span>Email：
				</td>
				<td>
					<input class="inputbox" id="email" name="email" onchange="checkEm()" type="text" value="${email}"/>
				</td>
				<td>
					<span class="input_err" id="email_err"></span>
				</td>
			</tr>
			<tr>
				<td class="right">
					<span class="req_mark">*</span>Password：
				</td>
				<td>
					<input class="inputbox" id="password" name="password" onchange="checkPs()" type="password" value="${password}"/>
				</td>
				<td>
					<span class="input_err" id="password_err"></span>
				</td>
			</tr>
			<tr>
				<td class="right">
					<span class="req_mark">*</span>Real Name：
				</td>
				<td>
					<input class="inputbox" id="name" name="name" onchange="checkNa()" type="text" value="${name}"/>
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
					<input type="radio" checked="checked" name="sex" value="1"/>
					<span class="textbox">Male</span>
					<input type="radio" name="sex" value="2"/>
					<span class="textbox">Female</span>
				</td>
			</tr>
		</table>
		<div class="btn_wrap">
			<input class="register_btn" type="submit" value="sign up now" />
		</div>
	</form>
	<div class="reg_image">
		<img src="images/back_school.jpg">
	</div>
</div>