var uploadBtn;
var albumControl;
var photos = [];
var _index = 0;

$(function() {
	
	$("#bookName .inputbox").change(function(){
		checkBookName();
	});
	
	$("#bookDes .inputbox").val("");
	$("#bookDes .inputbox").change(function(){
		checkBookDes();
	});
	
	$("#submit").click(function(){
		submitShare();
	});
	
	albumControl = new JAlbum.Album("photo_album", {		
		imageWidth: 170,
		imageHeight: 130, 
		triggerVisible: true,
		triggerText: "设置为默认图像"});
	albumControl.triggerClickEvent = onAlbumTriggerClickEvent;
	
	var uploadUrl = "ajax/album/share/book/uploadphoto";
	uploadBtn = new JUpload.UploadButton("upload_btn", {text: "图片上传", fileId: "file", url: uploadUrl});
	uploadBtn.uploadResultEvent = function(result, data){
		if (data){
			data.id = _index;
			_index++;
			
			albumControl.addImage({id: data.id, src: data.middleImgUrl, href: data.middleImgUrl});
			photos.push(data);
		}
	};
	
	uploadBtn.beforeUploadEvent = function(){
		albumControl.showUploading();
	};
});

function submitShare(){
	var bRet = (checkBookName() && checkBookDes());
	if (bRet){
		var data = {
				stuffName: $("#bookName .inputbox").val(),
				stuffDes: $("#bookDes .inputbox").val(),
				photos: photos,
				targetClassId: $("#shareScope select").val(),
		};
		
		util.getJSONAsync2('ajax/share/book/create', data, onCreateShare);
	}
}

function onCreateShare(data){
if (data != null && data.status){
	util.popDlg("创建成功！");
}
}

function checkBookName(){
	var bRet = false;
	
	var str = $("#bookName .inputbox").val();
	$("#bookName .inputbox").val(util.trim(str));
	if (!util.hasVal(str)){
		$("#bookName .input_err").html("书籍名称不能为空!");
	}else{
		$("#bookName .input_err").html("");
		
		bRet = true;
	}
	
	return bRet;
}

function checkBookDes(){
	var bRet = false;
	
	var str = $("#bookDes .inputbox").val();
	$("#bookDes .inputbox").val(util.trim(str));
	if (!util.hasVal(str)){
		$("#bookDes .input_err").html("描述不能为空!");
	}else{
		$("#bookDes .input_err").html("");	
		
		bRet = true;
	}
	
	return bRet;
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

//function onGetClassAvatarAlbum(data, status){
//	if (data != null){
//		var album = data.data;
//		var photo;
//		for (var i = 0; i < album.photos.length; i++){
//			photo = album.photos[i];
//			
//			var avatar = photo.mark == "avatar" ? true : false;
//			albumControl.addImage({id: photo.id, src: photo.middleImgUrl, href: photo.bigImgUrl, avatar: avatar});		
//		}
//	}
//	
//	var uploadUrl = "ajax/album/" + data.data.id + "/addPhoto";
//	uploadBtn = new JUpload.UploadButton("upload_btn", {text: "我要上传", fileId: "file", url: uploadUrl});
//	uploadBtn.uploadResultEvent = function(result, data){
//		if (data){
//			albumControl.addImage({id: data.id, src: data.middleImgUrl, href: data.middleImgUrl});			
//		}
//	};
//}

