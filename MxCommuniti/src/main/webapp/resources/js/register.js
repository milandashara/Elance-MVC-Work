// JavaScript Document

function checkEm()
{
	var ret = false;
	
	var v = document.getElementById("email").value;
	document.getElementById("email").value = util.trim(v);
	if (!util.hasVal(v)){
		document.getElementById("email_err").innerHTML = "请输入您的邮箱！";
	}
	else if(!util.checkEm(v)){
		document.getElementById("email_err").innerHTML = "请输入真实的Email地址！";
	}
	else{
		document.getElementById("email_err").innerHTML = "";
		$.ajax({
			url: "ajax/verify",
			async:false,
			data: {"email": v},
			dataType: "json",
			success: function(data){
				if (data.status){
					document.getElementById("email_err").innerHTML = "<img src='images/valid.png'/>";
				}else{
					document.getElementById("email_err").innerHTML = data.error;		
				}
				
				ret = data.status;
			}
		});
	}
	
	return ret;
}

function checkPs()
{
	var ret = false;
	
	var v =document.getElementById("newPassword").value;
	document.getElementById("newPassword").value = util.trim(v);
	if (!util.hasVal(v)){
		document.getElementById("password_err").innerHTML = "请输入您的密码！";
	}
	else if (!util.checkPs(v)){
		document.getElementById("password_err").innerHTML = "密码安全太低，请重设！";
	}
	else{
		document.getElementById("password_err").innerHTML = "<img src='images/valid.png'/>";
		ret = true;
	}
	
	return ret;
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

function validate(){
	var ret = true;
	ret = checkEm() && ret;
	ret = checkPs() && ret;
	ret = checkNa() && ret;
	return ret;
}