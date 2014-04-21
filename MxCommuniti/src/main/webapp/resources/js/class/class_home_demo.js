var _select_desk = null;
var _upload = null;
var _msg_pageNumber = 1;
var _msg_pageSize = 15;
var _msg_startTime = (new Date()).getTime();

var _demo_data = null;

$(function(){
	
	_demo_data = util.getJSON("demo/class_demo.json");
	
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
		
	$("#content_box").load('ajax/class/classroom', initClassroom);	
	
	//获取班级头像
	$(".myclass_avatar_box .class_img").attr("href", "javascript:void(0);");
	$(".myclass_avatar_box .class_img img").attr("src", _demo_data.myclass.avatar);	
});

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
		
		//模拟发布成功
		var msg = {
			senderName: $("#g_dom_user_name_a").html(),
			senderAvatar: $("#g_dom_user_avatar_img").attr("src"),
			content: content,
			album: {photos:images},
			createTime: new Date()
		};
		
		$(".message_content textarea").val("");
		$("#picUploadDiv").html("");
		$("#picUploadDiv").hide();
		
		util.popDlg("发布成功", true);
		addMessage(msg, true);
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
	
//	_msg_pageNumber = 1;	
//	var data = {
//			classId: g_class_id,
//			startTime: _msg_startTime,
//			pageNumber: _msg_pageNumber,
//			pageSize: _msg_pageSize
//		};
//	util.getJSONAsync('ajax/message/getMessages', data, onGetClassMessagesAjax);
	
	$("#message_query_more").click(onMessageQueryMore);
	
	initDesks(_demo_data.desks);
	initMessages(_demo_data.messages);
}

//function onGetClassMessagesAjax(result){
//	if (result.status){
//		var msgList = result.data;
//		var msg = null;
//		for (var i = 0; i < msgList.length; i++){
//			msg = msgList[i];
//			
//			addMessage(msg);
//		}
//	}
//}

function initMessages(messages){
	var message = null;
	for (var i = 0; i < messages.length; i++){
		message = messages[i];
		addMessage(message);
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
	myJClass.removeDesk(desk);	
}

function onDeskSelect(desk){
	_select_desk = desk;
	
	if (_select_desk != null){
		_select_desk.setStudent(new JClass.Student("我", 1, "999999999", new Date()));
		myJClass.drawDesk(_select_desk);
	}
	myJClass.enableSelectable(false);
	
	util.popDlg("座位绑定成功", true);
}

function initDesks(desks){
	var hasEmptyDesk = false;
	var hasSeat = false;
	var jDesk;
	for (var i = 0; i < desks.length; i++){
		desk = desks[i];
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
	
	if (hasSeat){
		myJClass.enableSelectable(false);		
	}else{
		if (!hasEmptyDesk){
			onClassToolsAddDeskClick();	
		}
		myJClass.enableSelectable(true);				
	}
}

//function onGetDesksAjax(data, status){
//	myJClass.clearDesks();
//	
//	var hasEmptyDesk = false;
//	var hasSeat = false;
//	if (data.status){
//		var desk;
//		var jDesk;
//		for (var i = 0; i < data.data.length; i++){
//			desk = data.data[i];
//			jDesk = new JClass.Desk(desk.id, new JClass.Pixel(desk.x, desk.y));
//			if (desk.userName != null && desk.userName != ""){
//				jDesk.setStudent(new JClass.Student(desk.userName, desk.userSex, desk.userCode, desk.lastVisit));
//			}else{
//				hasEmptyDesk = true;
//			}
//			myJClass.addDesk(jDesk);
//			
//			if (desk.userCode == g_user_code){
//				hasSeat = true;
//			}
//		}
//	}
//	
//	if (hasSeat){
//		myJClass.enableSelectable(false);		
//	}else{
//		if (!hasEmptyDesk){
//			onClassToolsAddDeskClick();	
//		}
//		myJClass.enableSelectable(true);				
//	}
//}

function onClassToolsDragDeskClick(){
	var status = !myJClass.draggable;
	myJClass.enableDraggable(status);
	myJClass.enableDeletable(status);
	
	
	
	if (status){
		$("#class_adjust_popup").show();		
	}else{
		$("#class_adjust_popup").hide();		
	}	
}

function onClassToolsAddDeskClick(){	
	myJClass.addDesk(new JClass.Desk((new Date).getTime(), new JClass.Pixel(100, 550), null));
}

function onClassToolsShowNameClick(){
	var has = $("#class_tool_showname").hasClass("check-on");
	
	myJClass.showDeskTitle(!has);	
	$("#class_tool_showname").toggleClass("check-on");
}

function initMemebers(){
//	var data = {
//			classCode: g_class_code
//	};
//	
//	util.getJSONAsync('ajax/class/getClassMembers', data, onGetMembers);
	
	initMembers(_demo_data.members);
}

function initMembers(members){
	var member = null;
	var memberHtml = "";
	for (var i = 0; i < members.length; i++){
		member = members[i];
		memberHtml = "<div class='member_info_box'>"
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

//function onGetMembers(data, status){
//	if (data != null){
//		for (var i = 0; i < data.data.length; i++){
//			var member = data.data[i];
//			var memberHtml = "<div class='member_info_box'>"
//				+ "<div class='member_info_head'>"
//				+ "<img src='" + member.smallAvatar + "'>"
//				+ "<ul>"
//				+ "<li>" + member.name + "</li>"
//				+ "<li>已加入</li>"
//				+ "</ul>"
//				+ "</div>"
//				+ "<div class='member_info_des'>"
//				+ "<span>简介:新时代不羁青年</span>"
//				+ "</div>"
//				+ "</div>";
//			$("#myclass_members_box").prepend(memberHtml);			
//		}
//	}
//}

function initTrend(){
	
}

function initStudy(){
	
}