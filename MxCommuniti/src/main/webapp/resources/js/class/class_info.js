var uploadBtn;
var albumControl;

$(function() {
	albumControl = new JAlbum.Album("photo_album", {
		imageWidth: 165,
		imageHeight: 130, 
		triggerVisible: true,
		triggerText: "设置为头像"});
	albumControl.triggerClickEvent = onPhotoClickEvent;
	
	var data = {
			classId: g_class_id
	};
	
	util.getJSONAsync('ajax/album/getClassAvatarAlbum', data, onGetClassAvatarAlbum);
});

function onPhotoClickEvent(photoId){
	var data = {
			classId: g_class_id,
			photoId:photoId
	};
	
	albumControl.setAvatar(photoId);
	util.getJSONAsync('ajax/album/setClassAvatar', data, onSetClassAvatar);
}

function onSetClassAvatar(data){
	if (data != null && data.status){
		util.popDlg("设置成功！");
	}
}

function onGetClassAvatarAlbum(data, status){
	if (data != null){
		var album = data.data;
		var photo;
		for (var i = 0; i < album.photos.length; i++){
			photo = album.photos[i];
			
			var avatar = photo.mark == "avatar" ? true : false;
			albumControl.addImage({id: photo.id, src: photo.middleImgUrl, href: photo.bigImgUrl, avatar: avatar});		
		}
	}
	
	var uploadUrl = "ajax/album/" + data.data.id + "/add";
	uploadBtn = new JUpload.UploadButton("upload_btn", {text: "我要上传", fileId: "file", url: uploadUrl});
	uploadBtn.uploadResultEvent = function(result, data){
		if (data){
			albumControl.addImage({id: data.id, src: data.middleImgUrl, href: data.middleImgUrl});			
		}
	};
	
	uploadBtn.beforeUploadEvent = function(){
		albumControl.showUploading();
	};
}

