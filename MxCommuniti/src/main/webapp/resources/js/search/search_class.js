var _myClassList = [];
var _myPendingClassList=[];

$(function(){
	$("#schoolYear").html("");
	
	var now= new Date(); 
	var year = now.getFullYear();
	$("#schoolYear").append("<option value='0'>" + "全部" + "</option>");
	for (var i = year; i >= 1970; i--){
		$("#schoolYear").append("<option value=\"" + i + "\">" + i + "</option>");
	}
	
	$("#schoolName").click(function(){
		_schoolDlg.open(onSelectSchool);
	});
	
	util.getJSONAsync('ajax/user/getUserClass', {}, onGetUserClassResult);
	util.getJSONAsync('ajax/user/getUserPendingClass', {}, onGetUserPendingClassResult);
});

function onGetUserClassResult(data, status){
	if (data != null){
		_myClassList = data.data;
	}
}

function onGetUserPendingClassResult(data, status){
	if (data != null){
		_myPendingClassList = data.data;
	}
}
function isExist(classList, myClass){
	for (var i = 0; i < classList.length; i++){
		if (classList[i].id == myClass.id){
			return true;
		}
	}
	
	return false;
}

function onSelectSchool(code, name){
	 $("#schoolName").val(name);
	 $("#schoolCode").val(code);	 
}

function onSearchResult(data, status){
	$(".result_box ul").html("");
	if (data != null){
		if (data.object != null && (Object.prototype.toString.call(data.object) == '[object Array]')){
			var myClass = null;
			for (var i = 0; i < data.object.length; i++){
				myClass = data.object[i];
				
				var classNode = "<li id='li_" + myClass.id + "'>" +
				"<div class='avatar_box'>" +
				"<a class='class_avatar'>" + 
				"<img src='images/base/class_photo_100.png'></img>" + 
				"</a>" +
				"</div>" +
				"<div class='class_info'>" +
				"<dl>" + 
				"<dd>" +	
				"<strong>" + myClass.name +"</strong>" +	
				"</dd>"	+	
				"<dd>"	+
				myClass.schoolName + "&nbsp;&nbsp;&nbsp;&nbsp;" + myClass.schoolYear + "年" +
				"</dd>" +
				"</dl>"	 +	
				"</div>" +
				"<div class='class_action'>";
				if (isExist(_myClassList, myClass)){
					classNode += "<span style='color: green;font-weight:bold;'>已经加入</span>";	
				}
				else if(isExist(_myPendingClassList, myClass)){
					classNode += "<span style='color: red;font-weight:bold;'>等待批准</span>";
				}
				else{
					classNode += "<button onclick='onJoinClass("+ myClass.id +")'>申请加入+</button>";	
				}
				
				classNode += "</div>" + 
				"<div style='clear:both;'></div>" +
				"</li>";
				
				$(".result_box ul").append(classNode);	
				
				updateClassAvatar(myClass.id);
			}
		}
	}
}

function updateClassAvatar(classId){
	//获取班级头像
	var data = {
			classId: classId
	};
	
	util.getJSONAsync('ajax/album/getClassAvatar', data, function(result){
		if (result != null){
			//$(".myclass_avatar_box .class_img img").attr("src", data.data.middleImgUrl);	
			$(".result_box ul").find("#li_" + classId + " img").attr("src", result.data.smallImgUrl);
		}
	});	
}

function onJoinClassResult(result){
	if (result.status == true){
		$(".result_box ul").find("#li_" + result.data).find(".class_action").html("<span style='color: red;font-weight:bold;'>等待审批</span>");
	}
}

function onJoinClass(classId){
	var data = {
		classId: classId	
	};
	
	util.getJSONAsync('ajax/class/join', data, onJoinClassResult);
}

function onSearchClass(){
	var data = {
		className: 	$("#className").val(),
		schoolType: $("#schoolType").val(),
		schoolCode:  $("#schoolCode").val(),
		schoolYear: $("#schoolYear").val(),
		pageNumber: 1,
		pageSize: 10
	};
	
	util.getJSONAsync('ajax/class/search', data, onSearchResult);
}