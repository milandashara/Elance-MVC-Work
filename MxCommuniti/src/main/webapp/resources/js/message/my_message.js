var _msg_pageNumber = 1;
var _msg_pageSize = 20;
var _msg_startTime = (new Date()).getTime();

$(function(){	
	var data = {
			startTime: _msg_startTime,
			pageNumber: _msg_pageNumber,
			pageSize: _msg_pageSize
		};
	util.getJSONAsync('ajax/message/messagebox', data, onGetMessageBoxAjax);
});

function onGetMessageBoxAjax(result){
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
	html+="<div class='msg_time right'><a>" + dateStr + "</a></div>";
	
	html += "</div></div>";			
	
	if (inverse != undefined && inverse){
		$("#myclass_msg_box").prepend(html);	
	}else{
		$("#myclass_msg_box").append(html);	
	}

	
	$("#" + ulId + " a").lightBox({txtImage:'图片',txtOf:'/'});	
}