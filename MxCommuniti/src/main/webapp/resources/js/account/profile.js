$(function(){
	setTab("info");
});

function setTab(tab){
	$(".account-profile-tabs li").removeClass("focus");
	$(".tab-content").hide();
	if (tab == "info"){
		$(".account-profile-tabs #info_tab").addClass("focus");
		$("#info_content").show();
	}
	else if (tab == "avatar"){
		$(".account-profile-tabs #avatar_tab").addClass("focus");	
		$("#avatar_content").show();
	}
	else if (tab == "password"){
		$(".account-profile-tabs #password_tab").addClass("focus");	
		$("#password_content").show();
	}
}

function checkNa()
{
	var ret = false;
	
	var v =document.getElementById("name").value;
	document.getElementById("name").value = util.trim(v);
	if (!util.hasVal(v)){
		document.getElementById("name_err").innerHTML = "请输入您的姓名！";
	}
	else{
		document.getElementById("name_err").innerHTML = "<img src='images/valid.png'/>";
		ret = true;
	}
	
	return ret;
}

function checkOldPs()
{
	var ret = false;
	
	var v = document.getElementById("oldPassword").value;
	v = document.getElementById("oldPassword").value = util.trim(v);
	if (!util.hasVal(v)){
		document.getElementById("oldpPassword_err").innerHTML = "请输入您的密码！";
	}else{
		document.getElementById("oldpPassword_err").innerHTML = "";
		ret = true;
	}
	
	return ret;
}

function checkNewPs()
{
	var ret = false;
	
	var v = document.getElementById("newPassword").value;
	v = document.getElementById("newPassword").value = util.trim(v);
	if (!util.hasVal(v)){
		document.getElementById("newPassword_err").innerHTML = "请输入您的密码！";
	}
	else if (!util.checkPs(v)){
		document.getElementById("newPassword_err").innerHTML = "密码安全太低，请重设！";
	}
	else{
		document.getElementById("newPassword_err").innerHTML = "<img src='images/valid.png'/>";
		ret = true;
	}
	
	return ret;
}

function checkConfirmPs(){
	var ret = false;
	
	var newPs = document.getElementById("newPassword").value;
	var confirmPs = document.getElementById("confirmPassword").value;
	confirmPs = document.getElementById("confirmPassword").value = util.trim(confirmPs);
	if (confirmPs != newPs ){
		document.getElementById("confirmPassword_err").innerHTML = "两次输入密码不一致，请重新输入！";
	}else{
		document.getElementById("confirmPassword_err").innerHTML = "";
		ret = true;		
	}
	
	return ret;
}

function validate(){
	var ret = true;
	ret = checkNa() && ret;
	return ret;
}

function changePassword(){
	var ret = true;
	ret = checkOldPs() && ret;
	ret = checkNewPs() && ret;
	ret = checkConfirmPs() && ret;
	
	if (ret){
		var data = {
				oldPassword: document.getElementById("oldPassword").value,
				newPassword: document.getElementById("newPassword").value
			};
		util.getJSONAsync('ajax/user/updatePassword', data, onUpdatePasswordAjax);		
	}
}

function onUpdatePasswordAjax(result){
	if (result.status){
		document.getElementById("oldPassword").value = "";
		document.getElementById("newPassword").value = "";
		document.getElementById("confirmPassword").value = "";
		document.getElementById("oldpPassword_err").innerHTML = "";
		document.getElementById("newPassword_err").innerHTML = "";
		document.getElementById("confirmPassword_err").innerHTML = "";
		
		util.popDlg("密码更新成功", true);
	}
}