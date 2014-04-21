var _select_desk = null;
var _upload = null;
var _msg_pageNumber = 1;
var _msg_pageSize = 15;
var _msg_startTime = (new Date()).getTime();

$(function(){
	$(".message_content textarea").val("");
	$(".message_content textarea").bind("input propertychange", function() {
		var val = $(".message_content textarea").val();
		if (val != null && val != ""){
			$("#publish_btn a").addClass("active");
		}else{
			$("#publish_btn a").removeClass("active");
		}
	});
	
	$("#publish_btn a").click(onMessagePublish);
	$("#publish_limits #publish_setting").click(onMessageLimits);
	$("#publish_limits #publish_menu_class").click(function(){
		$("#publish_limits #publish_setting .publish_value").html("仅班级可见");
		
		onMessageLimits();
	});
	
	$("#publish_limits #publish_menu_public").click(function(){
		$("#publish_limits #publish_setting .publish_value").html("公开");
		
		onMessageLimits();
	});	
	
	$("#func_picUpload").click(function(){
		if ($("#picUploadDiv").is(":hidden")){
			_upload = new JUpload.Upload("picUploadDiv");
			$("#picUploadDiv").show();
		}
	});
	
	$("#navi_classroom").click(function(){		
		$(".g_navi_min_2 a").removeClass("active");
		$("#navi_classroom").addClass("active");
		$("#content_box").load('ajax/class/classroom', initClassroom);	
		return false;
	});
	
	$("#navi_members").click(function(){
		$(".g_navi_min_2 a").removeClass("active");
		$("#navi_members").addClass("active");
		$("#content_box").load('ajax/class/members', initMemebers);		
		return false;
	});
	
	$("#navi_trend").click(function(){
		$(".g_navi_min_2 a").removeClass("active");
		$("#navi_trend").addClass("active");
		$("#content_box").load('ajax/class/ranking', initTrend);		
		return false;
	});
	
//	$("#navi_study").click(function(){
//		$(".g_navi_min_2 a").removeClass("active");
//		$("#navi_study").addClass("active");
//		$("#content_box").load('ajax/class/study', initStudy);		
//		return false;
//	});	
	
	
	$("#content_box").load('ajax/class/classroom', initClassroom);	
	
	var data = {
			classCode: g_class_code
	};
	
	util.getJSONAsync('ajax/class/getClassPendingMembers', data, onGetPendingMembers);
	
	//获取班级头像
	var data = {
			classId: g_class_id
	};
	
	util.getJSONAsync('ajax/album/getClassAvatar', data, onGetClassAvatar);
});

function onGetClassAvatar(data, status){
	if (data != null){
		$(".myclass_avatar_box .class_img img").attr("src", data.data.middleImgUrl);		
	}
}

function onMessageLimits(){
	$("#publish_limits #publish_setting").toggleClass("arrow_turn");
	$("#publish_limits #publish_menu").toggle();
}

function onMessagePublish(){
	var val = $(".message_content textarea").val();
	val = util.trim(val);
	$(".message_content textarea").val(val);
	if (val != null && val != ""){
		var content = val;
		var images = [];
		if (_upload != null){
			images = _upload.getFiles();		
		}
		var data = {
				classId: g_class_id,
				content: content,
				photos: images
			};
		
		if ($("#publish_limits #publish_setting .publish_value").html() == "公开"){
			data.classId = 0;
		}
		
		util.getJSONAsync2('ajax/message/publish', data, onMessagePublishAjax);
	}
}

function onMessagePublishAjax(result){
	if (result.status){
		$(".message_content textarea").val("");
		$("#picUploadDiv").html("");
		$("#picUploadDiv").hide();
		
		util.popDlg("发布成功", true);
		addMessage(result.data, true);
	}else{
		util.popDlg("发布失败", false);		
	}
}

function onMessageQueryMore(){
	_msg_pageNumber++;	
	var data = {
			classId: g_class_id,
			startTime: _msg_startTime,
			pageNumber: _msg_pageNumber,
			pageSize: _msg_pageSize
		};
	util.getJSONAsync('ajax/message/getMessages', data, onGetClassMessagesAjax);
}

function initClassroom(){
	myJClass = new JClass.ClassRoom("myclass_classroom");
	myJClass.setClassName(g_class_name);
	myJClass.deleteEventCallback = onDeskDelete;
	myJClass.selectEventCallback = onDeskSelect;
	
	$("#class_tools_dragDesk").click(onClassToolsDragDeskClick);
	$("#class_tools_addDesk").click(onClassToolsAddDeskClick);
	$("#class_tool_showname").click(onClassToolsShowNameClick);		
	
	var data = {
			classCode: g_class_code
		};
	util.getJSONAsync('ajax/class/getDesks', data, onGetDesksAjax);
	
	_msg_pageNumber = 1;	
	var data = {
			classId: g_class_id,
			startTime: _msg_startTime,
			pageNumber: _msg_pageNumber,
			pageSize: _msg_pageSize
		};
	util.getJSONAsync('ajax/message/getMessages', data, onGetClassMessagesAjax);
	
	$("#message_query_more").click(onMessageQueryMore);
}

function onGetClassMessagesAjax(result){
	if (result.status){
		var msgList = result.data;
		var msg = null;
		for (var i = 0; i < msgList.length; i++){
			msg = msgList[i];
			
			addMessage(msg);
		}
	}
}

function addMessage(msg, inverse){
	var html = "<div class='g_msg_item'>" +
	"<div class='avatar'>" +
	"<a><img src='" + msg.senderAvatar + "'></a>" +	
	"</div>" +
	"<div class='content'>" +
	"<div class='user_info'><a>" + msg.senderName + "</a></div>" + 
	"<div class='msg_text'>" + msg.content + "</div>";

	var ulId = 'msg_image_' + msg.id;
	if (msg.album && msg.album.photos.length > 0){
		html += "<ul class='msg_image' id='" + ulId + "'>";
		for (var j = 0; j < msg.album.photos.length; j++){
			html += "<li><div><a href='"+ msg.album.photos[j].bigImgUrl +"' title='图片'><img src='" + msg.album.photos[j].middleImgUrl + "'></a></div></li>";
		}
		html += "</ul>";
	}
	
	var date = new Date(msg.createTime);
	var dateStr = date.format('MM月dd日 hh:mm');
	html+="<div class='msg_time'><a>" + dateStr + "</a></div>";
	
	html += "</div></div>";			
	
	if (inverse != undefined && inverse){
		$("#myclass_msg_box").prepend(html);	
	}else{
		$("#myclass_msg_box").append(html);	
	}

	
	$("#" + ulId + " a").lightBox({txtImage:'图片',txtOf:'/'});	
}

function onDeskDelete(desk){
	var data = {
			deskId: desk.id
		};
	util.getJSONAsync('ajax/class/deleteDesk', data, null);
	myJClass.removeDesk(desk);	
}

function onDeskSelect(desk){
	_select_desk = desk;
	
	var data = {
			deskId: desk.id
		};
	util.getJSONAsync('ajax/class/bindDeskUser', data, onBindDeskUserAjax);		
}

function updateDeskPosition(){
	var param = [];
	
	var desks = myJClass.getDesks();
	var desk = null;
	for (var i = 0; i < desks.length; i++){
		desk = {id: desks[i].id, x: desks[i].px.x, y: desks[i].px.y};
		param.push(desk);
	}
	
	util.getJSONAsync2('ajax/class/updateDeskPositions', param, null);
}

function onGetDesksAjax(data, status){
	myJClass.clearDesks();
	
	var hasEmptyDesk = false;
	var hasSeat = false;
	if (data.status){
		var desk;
		var jDesk;
		for (var i = 0; i < data.data.length; i++){
			desk = data.data[i];
			jDesk = new JClass.Desk(desk.id, new JClass.Pixel(desk.x, desk.y));
			if (desk.userName != null && desk.userName != ""){
				jDesk.setStudent(new JClass.Student(desk.userName, desk.userSex, desk.userCode, desk.lastVisit));
			}else{
				hasEmptyDesk = true;
			}
			myJClass.addDesk(jDesk);
			
			if (desk.userCode == g_user_code){
				hasSeat = true;
			}
		}
	}
	
	if (hasSeat){
		myJClass.enableSelectable(false);		
	}else{
		if (!hasEmptyDesk){
			onClassToolsAddDeskClick();	
		}
		myJClass.enableSelectable(true);				
	}
}

function onBindDeskUserAjax(result){
	if (result.status == true){
		if (_select_desk != null){
			_select_desk.setStudent(new JClass.Student(result.data.userName, result.data.userSex, result.data.userCode, result.data.lastVisit));
			myJClass.drawDesk(_select_desk);
		}
		myJClass.enableSelectable(false);
		
		util.popDlg("座位绑定成功", true);
	}
}

function onClassToolsDragDeskClick(){
	var status = !myJClass.draggable;
	myJClass.enableDraggable(status);
	myJClass.enableDeletable(status);
	
	
	
	if (status){
		$("#class_adjust_popup").show();		
	}else{
		$("#class_adjust_popup").hide();		
	}	
	
	if (!status){
		updateDeskPosition();
	}
}

function onClassToolsAddDeskClick(){
	var data = {
		classCode: g_class_code,
		x: 100,
		y: 550
	};
	util.getJSONAsync('ajax/class/addDesk', data, onAddDeskAjax);
}

function onClassToolsShowNameClick(){
	var has = $("#class_tool_showname").hasClass("check-on");
	
	myJClass.showDeskTitle(!has);	
	$("#class_tool_showname").toggleClass("check-on");
}

function onAddDeskAjax(data, status){
	if (data.status){
		myJClass.addDesk(new JClass.Desk(data.data.id, new JClass.Pixel(data.data.x, data.data.y), null));
	}
}

function initMemebers(){
	var data = {
			classCode: g_class_code
	};
	
	util.getJSONAsync('ajax/class/getClassMembers', data, onGetMembers);
}

function onGetMembers(data, status){
	if (data != null){
		for (var i = 0; i < data.data.length; i++){
			var member = data.data[i];
			var memberHtml = "<div class='member_info_box'>"
				+ "<div class='member_info_head'>"
				+ "<img src='" + member.smallAvatar + "'>"
				+ "<ul>"
				+ "<li>" + member.name + "</li>"
				+ "<li>已加入</li>"
				+ "</ul>"
				+ "</div>"
				+ "<div class='member_info_des'>"
				+ "<span>简介:新时代不羁青年</span>"
				+ "</div>"
				+ "</div>";
			$("#myclass_members_box").prepend(memberHtml);			
		}
	}
}

function onGetPendingMembers(data, status){
	if (data != null){
		for (var i = 0; i < data.data.length; i++){
			var member = data.data[i];
			var memberHtml = "<a id='pending_" + member.id + "' style='display:block;position:relative;padding:3px 0;'>"
				+ "<span style='position:absolute; left: 55px;'>" + member.name + "</span>"
				+ "<img alt='' src='" + member.bigAvatar +"' style='width: 50px; height: 50px;'>"
				+ "<button onclick=\"approvePending('" + member.id + "')\">批准加入</button>"
				+ "<button onclick=\"refusePending('" + member.id + "')\">拒绝加入</button>"
				+ "</a>";
			
			$("#class_pending_box").show();
			$("#class_pending_box").append(memberHtml);
		}
	}
}

function approvePending(userId){
	var data = {
			classId: g_class_id,
			userId: userId
	};
	
	$("#pending_" + userId).remove();
	util.getJSONAsync('ajax/class/approvePending', data, null);	
}

function refusePending(memberCode){
	var data = {
			classCode: g_class_code,
			memberCode: memberCode
	};
	
	$("#pending_" + memberCode).remove();
	util.getJSONAsync('ajax/class/refusePending', data, null);	
}

function initTrend(){
	
}

function initStudy(){
	
}