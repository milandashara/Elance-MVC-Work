// JavaScript Document
var JTable = util.Class({
	parent: null,
	template: null,
	initialize: function(div, options){
		this.parent = "#" + div;
		$(this.parent).html("<div class='JTable-Box'><table>"
			+ "<tr class='title-row'>"
			+	"<td style='width: 300px;'><span>名称</span></td>"
			+	"<td style='width: 100px;'><span>创建时间</span></td>"
			+	"<td style='width: 100px;'><span>状态</span></td>"
			+	"<td style='width: 100px;'><span>管理</span></td>"
			+	"<td style='width: 100px;'><span>操作</span></td>"
			+"</tr></table></div>");
		
		if (options){
			if (options){
				if (options.template){
					this.template = $("#" + options.template).html();
				}
			}
		}
	},
	adaptView: function(obj){
		var apple = {
			appleId: obj.id,
			appleName: obj.stuffName,
			appleUrl: "share/book/" + obj.id,
			appleDes: obj.stuffDes,
			createrName: obj.donorName,
			imgUrl: "images/base/default_photo_middle.png",
			imgHref: "images/base/default_photo_middle.png",
			status: "自由借阅"
			};
		
		var date = new Date(obj.createTime);
		var dateStr = date.format('yyyy-MM-dd hh:mm');
		apple.createTime = dateStr;
		
		var photo = null;
		if (obj.album && obj.album.photos && (obj.album.photos.length > 0)){
			for (var i = obj.album.photos.length - 1; i > -1; i--){
				photo = obj.album.photos[i];
				if (photo.mark == "default"){
					break;
				}
			}
		}
		
		if (photo){
			apple.imgUrl = photo.middleImgUrl;
			apple.imgHref = photo.bigImgUrl;
		}
		
		return apple;
	},
	addItem: function(obj){
		var bRet = false;
		
		var view = this.adaptView(obj);
		
		if (this.template){
			str = Mustache.render(this.template, view);
			$(this.parent).find(".JTable-Box table").append(str);
			bRet = true;
		}

		return bRet;
	},
	deleteItem: function(objId){
		$(this.parent).find(".JTable-Box table").find("#row_" + objId).remove();
	}
});

var mytable = null;

$(document).ready(function(){
	
	$("#navi_share").click(function(){		
		$(".g-common-navi-2 a").removeClass("active");
		$("#navi_share").addClass("active");
		naviMyShareInit();
	});
	
	$("#navi_reserve").click(function(){		
		$(".g-common-navi-2 a").removeClass("active");
		$("#navi_reserve").addClass("active");

		naviReserveInit();
	});
	
	$("#navi_history").click(function(){		
		$(".g-common-navi-2 a").removeClass("active");
		$("#navi_history").addClass("active");

		naviHistoryInit();
	});
	
	naviMyShareInit();
});

function naviReserveInit(){
	$("#share-manage-table").html("").hide();
}

function naviHistoryInit(){
	$("#share-manage-table").html("").hide();	
}

function naviMyShareInit(){
	$("#share-manage-table").html("").show();
	mytable = new JTable("share-manage-table", {template: "jshelf_template"});
	
	var data = {
			userId: g_user_id,
	};
	util.getJSONAsync('ajax/share/book/getUserShareBooks', data, function(result){
		if (result.status){
			var shareList = result.data;
			var share = null;
			for (var i = 0; i < shareList.length; i++){
				share = shareList[i];
				
				mytable.addItem(share);
			}
		}		
	});	
}

function deleteShareBook(bookId){
	 var r = confirm("您确认删除本条信息吗？");
	 if (r==true)
	 {
		 
		var data = {
			bookId: bookId,
		};
		util.getJSONAsync('ajax/share/book/delete', data, function(result){
			if (result.status){
				mytable.deleteItem(bookId);
				util.popDlg("删除成功！");
			}		
		});
	 }
}