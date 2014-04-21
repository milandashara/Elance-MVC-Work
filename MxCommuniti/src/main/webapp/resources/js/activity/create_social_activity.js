// JavaScript Document
var uploadBtn;
var albumControl;
var photos = [];
var _photoIndex = 0;
var _memberIndex = 1;

var memberStr = "";

$(function(){	
	$("#datepicker").datepicker({
		showButtonPanel: true,
		dateFormat: "yy年mm月dd日",
		autoSize: true,
		currentText: "Now",
		monthNames: [ "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月" ] 
	});
	
	$( "#datepicker" ).datepicker( "setDate", new Date());
	
	util.tipInput("member_0 input", "填写姓名");
	$("#members_list #member_0 a").click({index: 0}, deleteMemberItem);
	
	$("#add_member").click(addMemberItem);
	
	albumControl = new JAlbum.Album("photo_album", {		
		imageWidth: 170,
		imageHeight: 130
		});
	albumControl.triggerClickEvent = onAlbumTriggerClickEvent;
	
	var uploadUrl = "ajax/album/upload";
	uploadBtn = new JUpload.UploadButton("upload_btn", {text: "图片上传", fileId: "file", url: uploadUrl});
	uploadBtn.uploadResultEvent = function(result, data){
		if (data){
			data.id = _photoIndex;
			_photoIndex++;
			
			albumControl.addImage({id: data.id, src: data.middleImgUrl, href: data.middleImgUrl});
			photos.push(data);
		}
	};
	
	uploadBtn.beforeUploadEvent = function(){
		albumControl.showUploading();
	};
	
	$("#submit").click(function(){
		submitActivity();
	});
});

function addMemberItem(){
	$( "#members_list .member-add").before("<div class='member-item' id='member_" + _memberIndex +"'>"
			+ "<input type='text'/>"
			+ "<a title='删除节点'></a>"
			+ "</div>");
	
	$("#members_list #member_" + _memberIndex + " a").click({index: _memberIndex}, deleteMemberItem);
	
	util.tipInput("member_" + _memberIndex + " input", "填写姓名");

	_memberIndex++;	
}

function deleteMemberItem(event){
	$("#members_list #member_" + event.data.index).remove();
}

function onAlbumTriggerClickEvent(photoId){
	for (var i = 0; i < photos.length; i++){
		if (photos[i].id == photoId){
			photos[i].mark = "default";
			
			albumControl.setAvatar(photoId);
			break;
		}
	}
}

function checkName(){
	var bRet = false;
	
	var str = $("#activityName .inputbox").val();
	$("#activityName .inputbox").val(util.trim(str));
	if (!util.hasVal(str)){
		$("#activityName .input_err").html("活动名称不能为空!");
	}else{
		$("#activityName .input_err").html("");
		
		bRet = true;
	}
	
	return bRet;
}

function checkAddress(){
	var bRet = false;
	
	var str = $("#activityAddress .inputbox").val();
	$("#activityAddress .inputbox").val(util.trim(str));
	if (!util.hasVal(str)){
		$("#activityAddress .input_err").html("地点不能为空!");
	}else{
		$("#activityAddress .input_err").html("");
		
		bRet = true;
	}
	
	return bRet;
}

function checkTime(){
	var bRet = false;
	
	var currentDate = $( "#datepicker" ).datepicker("getDate");
	if (currentDate == null){
		$("#activityAdress .input_err").html("请选择日期!");
	}else{
		$("#activityAdress .input_err").html("");
		
		bRet = true;
	}
	
	return bRet;
}

function checkClass(){
	var bRet = false;
	
	var val = $("#activityClass select").val();
	if (val == "0"){
		$("#activityClass .input_err").html("请选择班级!");
	}else{
		$("#activityClass .input_err").html("");
		
		bRet = true;
	}
	
	return bRet;
}

function checkMembers(){
	var bRet = false;
	
	memberStr = "";
	$("#members_list .member-item").each(function(){
		var str = $(this).find("input").first().val();
		if (str && str != "填写姓名" && str !=""){
			memberStr += str;
			memberStr +=";";
		}
	});
	
	if (memberStr == ""){
		$("#activityMembers .input_err").html("请填写参加活动的人员！");
	}else{
		$("#activityMembers .input_err").html("");
		bRet = true;
	}
	
	return bRet;
}

function checkPhotos(){
	var bRet = false;
	
	if (photos.length < 1){
		$("#activityPhotos .input_err").html("至少上传一张活动照片！没有，就来张萌照吧...");
	}else{
		$("#activityPhotos .input_err").html("");
		bRet = true;
	}
	
	return bRet;
}

function submitActivity(){
	var bRet = (checkName() && checkAddress() && checkTime() && checkClass() && checkMembers() && checkPhotos());
	if (bRet){
		var data = {
				classId: $("#activityClass select").val(),
				activityName: $("#activityName .inputbox").val(),
				activityContent: "",
				activityLocation: $("#activityAddress .inputbox").val(),
				activityMemebers: memberStr,
				activityDate: $("#datepicker").datepicker("getDate"),
				photos: photos,
		};
		
		util.getJSONAsync2('ajax/activity/social/create', data, onCreateActivity);
	}
}

function onCreateActivity(data){
	if (data != null && data.status){
		util.popDlg("创建成功！");
	}
}