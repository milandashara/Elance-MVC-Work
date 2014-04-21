// JavaScript Document

var speller = null;
var spellerHard = 0;

$(function(){
	var username = $.cookie('username');
	var password = $.cookie('password');
	var savePassword = $.cookie('savePassword');
	if (savePassword == "true"){
		$("#email").val(username);
		$("#password").val(password);
		$("#save_password").prop("checked", true);
	}else{
		$("#email").val(username);
		$("#password").val("");
		$("#save_password").prop("checked", false);
	}
	
	initSpeller(0);
	
	util.getJSONAsync("ajax/game/jigsaw/record",{}, onGetJigsawRecordAjax);
});

function initSpeller(hard){
	spellerHard = hard;
	var row = 2, column = 2;
	var level = 200;
	var imgUrl = "";
	switch(hard){
	case 0:
		row = 2; column = 2;
		level = 1;
		imgUrl = "images/speller/0.jpg";
		break;
	case 1:
		row = 2; column = 3;
		imgUrl = "images/speller/1.jpg";
		break;
	case 2:
		row = 2; column = 4;
		imgUrl = "images/speller/2.jpg";
		break;
	case 3:
		row = 3; column = 3;
		imgUrl = "images/speller/3.jpg";
		break;
	case 4:
		row = 4; column = 4;
		imgUrl = "images/speller/4.jpg";
		break;
	case 5:
		row = 5; column = 5;
		imgUrl = "images/speller/5.jpg";
		break;
	case 6:
		row = 6; column = 6;
		imgUrl = "images/speller/6.jpg";
		break;
	case 7:
		row = 7; column = 7;
		imgUrl = "images/speller/7.jpg";
		break;
	}
	
	speller = new JSpeller("speller", {imgUrl: imgUrl, width: 580, height: 420, level: level, row: row, column: column});
	speller.finishEvent = function(){
		speller.stop();
		
		if (spellerHard > 0 && spellerHard < 8){
			util.getJSONAsync('ajax/game/jigsaw/updaterecord', {level: spellerHard}, onGetJigsawRecordAjax);	
		}
		
		spellerHard++;
		
		$("#speller").fadeOut(500);
		setTimeout(function(){
			$("#speller").fadeIn(500);
			
			if (spellerHard == 1){
				$("#speller").html("<div style='position: relative; width: 580px; height: 420px; background: url(images/speller/try.jpg);'>" 
						+ "<span style=\"position: relative; left: 20px; top: 40px; font-size: 32px;font-family: '微软雅黑';\">挑战拼图游戏，抢登塔顶？</span>"
						+ "<a class=\"big-button\" onclick=\"initSpeller(spellerHard);\">我要挑战</a>"
						+ "</div>");
			}
			else if (spellerHard < 7){	
				initSpeller(spellerHard);
			}
			else{
				show3D();	
			}
			
			switch(spellerHard){
			case 1:
				$("#tower_box").height(394);
				break;
			case 2:
				$("#tower_box").height(356);
				break;
			case 3:
				$("#tower_box").height(312);
				break;
			case 4:
				$("#tower_box").height(262);
				break;
			case 5:
				$("#tower_box").height(196);
				break;
			case 6:
				$("#tower_box").height(130);
				break;
			case 7:
				$("#tower_box").height(62);
				break;
			case 8:
				$("#tower_box").height(0);
				break;
			}
			
		}, 500);
	};
}

function onGetJigsawRecordAjax(result){
	if (result != null && result.data != null){
		$("#record_1").html(result.data.firstLevelNum);
		$("#record_2").html(result.data.secondLevelNum);
		$("#record_3").html(result.data.thirdLevelNum);
		$("#record_4").html(result.data.fourthLevelNum);
		$("#record_5").html(result.data.fifthLevelNum);
		$("#record_6").html(result.data.sixthLevelNum);
		$("#record_7").html(result.data.seventhLevelNum);
		
		for (var i = 1; i < spellerHard; i++){
			$("#record_" + i).css("color", "#ee5239");
		}
	}
}

function show3D(){
	var space = new Space3D.Space("speller");
	space.addImages([	
		[-900, 300, 600, "images/space3d/1.png"],
		[-800, 240, 550, "images/space3d/1.png"],
		[-600, 100, 500, "images/space3d/1.png"],
		[-500, 40, 450, "images/space3d/1.png"],
		[-300, -100, 400, "images/space3d/1.png"],
		[-200, -160, 350, "images/space3d/1.png"],
		[0, -300, 300, "images/space3d/1.png"],
		[100, -360, 250, "images/space3d/1.png"],
		
		[-600, 400, 700, "images/space3d/1.png"],
		[-500, 340, 650, "images/space3d/1.png"],
		[-300, 200, 600, "images/space3d/1.png"],
		[-200, 140, 550, "images/space3d/1.png"],
		[000, 0, 500, "images/space3d/1.png"],
		[100, -60, 450, "images/space3d/1.png"],
		[300, -200, 400, "images/space3d/1.png"],
		[400, -260, 350, "images/space3d/1.png"],
		
		[400, -180, 100, "images/space3d/3.gif"],
		[800, 480, 1400, "images/space3d/4.jpg"],
		]);
}

function login_validate(){
	var ret = false;
	var email = document.getElementById("email").value;
	email = util.trim(email);
	document.getElementById("email").value = email;
	
	var password = document.getElementById("password").value;
	password = util.trim(password);
	document.getElementById("password").value = password;
	
	if (email == ""){
		document.getElementById("erro_info").innerHTML = "请输入注册邮箱！";	
	}
	else if (password == "") {
		document.getElementById("erro_info").innerHTML = "请输入密码！";		
	}
	else {
		ret = true;
	}
	
	if (ret){
		var username = $("#email").val();
		var password = $("#password").val();
		
		if($("#save_password").prop("checked")){
			$.cookie('username', username, {expires : 30 });
			$.cookie('password', password, {expires : 30 });
			$.cookie('savePassword', "true", {expires : 30 });
		}else{
			$.cookie('username', username, {expires : 30 });
			$.cookie('password', null);
			$.cookie('savePassword', null);
		}
	}
	
	return ret;
}