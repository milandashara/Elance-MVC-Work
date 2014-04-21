window.JShelf = {};

JShelf.Util = {
	getImagePath: function(image){
		return util.getScriptLocation("jshelf.js") + "theme/images/" + image;
	}
};

JShelf.Shelf = util.Class({
	template: null,
	div: null,
	initialize: function(div, options){
		this.div = "#" + div;
		$(this.div).html("<ul class='jshelf-list'></ul>");
		
		if (options){
			if (options.template){
				this.template = $("#" + options.template).html();
			}
		}
	},
	adaptView: function(obj){
		var apple = {
			appleName: obj.stuffName,
			appleShortName: obj.stuffName,
			appleUrl: "share/book/" + obj.id,
			appleDes: obj.stuffDes,
			createrName: obj.donorName,
			imgUrl: JShelf.Util.getImagePath("default_img.png"),
			imgHref: JShelf.Util.getImagePath("default_img.png"),
			status: "自由借阅"
			};
		
		if (apple.appleShortName.length > 9){
			apple.appleShortName = apple.appleShortName.substr(0, 9) + "...";
		}
		
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
	addApple: function(obj){
		var bRet = false;
		
		var view = this.adaptView(obj);
		
		if (this.template){
			str = Mustache.render(this.template, view);
			$(this.div + " .jshelf-list").append(str);
			bRet = true;
		}

		return bRet;
	}
});