JUpload={};JUpload.Class=function(){var a=arguments[0];var b=typeof a.initialize=="function"?a.initialize:null;if(b!=null){b.prototype=a}return b};JUpload.Upload=JUpload.Class({div:null,imageObjs:[],closeEventCallback:null,initialize:function(b){this.div=document.getElementById(b);$(this.div).html("<div class='jupload-div'><div class='jupload-container'><a class='jupload-close'>X</a><div><div class='jupload-title'>本地上传</div><div class='jupload-pic-div'><div class='jupload-pic-des'>共0张，还能上传6张</div><ul><li class='add'><a>+</a><div><input id='file' name='file' type='file' style='width: 80px; height: 80px;'></input></div></li></ul></div></div></div><div class='arrow'></div></div>");var a=this;$(this.div).find(".jupload-close").click(function(){$(a.div).html("");if(a.closeEventCallback!=null){a.closeEventCallback()}});this.update()},addPic:function(b){$(this.div).find(".add").before("<li id='"+b+"' class='loading'><span><img src='js/JUpload/theme/images/loading.gif'>abc.gif</span><a>X</a></li>");var a=this;$(this.div).find("#"+b).find("a").click({id:b},function(c){$(a.div).find("#"+c.data.id).remove()})},update:function(){var a=this;$(this.div).find("#file").change(function(b){var c=new Date();var e=c.getTime();a.addPic(e);$.ajaxFileUpload({url:"ajax/album/upload",secureuri:false,fileElementId:"file",dataType:"json",success:function(h,d){var g=h.data.smallImgUrl;var f=h.data.smallImgUrl.lastIndexOf("/");if(f!=-1){g=g.substr(f+1);f=g.lastIndexOf(".");if(f!=-1){g=g.substr(0,f)}}a.imageObjs.push({id:g,obj:h.data});$("#"+e).removeClass("loading");$("#"+e).addClass("pic");$("#"+e).html("<img src='"+h.data.smallImgUrl+"'><a>X</a>");$("#"+e).attr("action-data",g);$("#"+e).find("a").click({id:e},function(i){$(a.div).find("#"+i.data.id).remove()});a.update()},error:function(f,d,g){alert("图片上传失败")}})})},getFiles:function(){var a=[];var b=this;$(this.div).find(".pic").each(function(){var d=$(this).attr("action-data");for(var c=0;c<b.imageObjs.length;c++){if(d==b.imageObjs[c].id){a.push(b.imageObjs[c].obj)}}});return a},CLASS_NAME:"JUpload.Upload"});JUpload.UploadButton=util.Class({text:null,fileId:null,url:null,uploadResultEvent:null,beforeUploadEvent:null,initialize:function(c,a){this.text="上传";this.fileId="fileUpload";$("#"+c).addClass("jupload-button-container");$("#"+c).html("<a class='jupload-button'><span></span><input type='file' id='fileUpload' name='fileUpload'/></a>");if(a!=null){if(a.text){this.text=a.text}if(a.fileId){this.fileId=a.fileId}if(a.url){this.url=a.url}}$("#"+c).find("span").html(this.text);$("#"+c).find("input").attr("id",this.fileId);$("#"+c).find("input").attr("name",this.fileId);var b=this;$(document).on("change","#"+c+" input",function(){var e=null;do{var h=$(this).val();if(!h){e="请选择图片";break}var g=".jpg.jpeg.gif.bmp.png.";var d=h.substr(h.lastIndexOf(".")+1).toLowerCase();if(g.indexOf("."+d+".")==-1){e="图片格式不正确";break}if(!b.url){e="请设置上传地址";break}if(b.beforeUploadEvent){b.beforeUploadEvent()}$.ajaxFileUpload({url:b.url,secureuri:false,fileElementId:b.fileId,dataType:"json",success:function(i,f){if(i){b.triggerUploadResult(true,i.data)}else{e="图片上传失败";b.triggerUploadResult(false,e)}},error:function(i,f,j){e="图片上传失败";b.triggerUploadResult(false,e)}})}while(false);if(e){b.triggerUploadResult(false,e)}})},triggerUploadResult:function(a,b){if(this.uploadResultEvent){this.uploadResultEvent(a,b)}},CLASS_NAME:"JUpload.UploadButton"});