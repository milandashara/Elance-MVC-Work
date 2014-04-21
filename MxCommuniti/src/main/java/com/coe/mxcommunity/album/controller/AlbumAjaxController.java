package com.coe.mxcommunity.album.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.coe.mxcommunity.account.entity.User;
import com.coe.mxcommunity.ajax.AjaxResult;
import com.coe.mxcommunity.album.service.AlbumService;
import com.coe.mxcommunity.entity.Album;
import com.coe.mxcommunity.entity.Photo;

@Controller
public class AlbumAjaxController {
    @RequestMapping(value="/ajax/album/upload", method = RequestMethod.POST)
	@ResponseBody
    public String uploadPhoto(@RequestParam("file") MultipartFile file)  {
		String result = "";
		
		Photo photo = null;
        if (!file.isEmpty()) {
        	try{
        		photo = AlbumService.uploadPhotoToServer(file, true);
        	}
        	catch (Exception e){   		
        	}
       }
        
       if (photo != null){
    	   result = "{status: true, error: \"\", data: {smallImgUrl: \""
    			   + photo.getSmallImgUrl() + "\", middleImgUrl: \"" 
    			   + photo.getMiddleImgUrl() + "\", bigImgUrl: \"" 
    			   + photo.getBigImgUrl() + "\"} }";
       }else{
    	   result = "{status: false}";
       }
        
       return result;
    }
	
	@RequestMapping(value="/ajax/album/getGroupAvatarAlbum", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResult getGroupAvatarAlbum(@RequestParam("groupId") Long groupId, HttpSession httpSession)
	{
		AjaxResult result = new AjaxResult();
		
		Album album = AlbumService.getGroupAvatarAlbum(groupId);
		if (album != null){
			result.setStatus(true);
			result.setData(album);
		}else{
			result.setStatus(false);
			result.setError("Fetch Failed!");
		}
		
        return result;
	}
	
	@RequestMapping(value="/ajax/album/getGroupAvatar", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResult getGroupAvatar(@RequestParam("groupId") Long groupId, HttpSession httpSession)
	{
		AjaxResult result = new AjaxResult();
		
		Photo photo = AlbumService.getGroupAvatar(groupId);
		if (photo == null){
			photo = new Photo();
			photo.setSmallImgUrl("images/base/group_photo_100.png");
			photo.setMiddleImgUrl("images/base/group_photo.png");
			photo.setBigImgUrl("images/base/group_photo.png");
		}
		
		result.setStatus(true);
		result.setData(photo);
		
        return result;
	}
	
	@RequestMapping(value="/ajax/album/setGroupAvatar", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResult setGroupAvatar(@RequestParam("groupId") Long groupId, @RequestParam("photoId") Long photoId, HttpSession httpSession)
	{
		AjaxResult result = new AjaxResult();
		
		boolean bRet = AlbumService.setGroupAvatar(groupId, photoId);
		
		result.setStatus(bRet);
		
        return result;
	}
	
	@RequestMapping(value="/ajax/album/{albumId}/add", method=RequestMethod.POST)
	@ResponseBody
	public String addPhoto(@RequestParam("file") MultipartFile file, @PathVariable Long albumId, HttpSession httpSession)
	{
		String result = "";
		
		User currentUser = (User)httpSession.getAttribute("GroupUser");
		Photo photo = AlbumService.uploadPhotoToServer(file);
		photo.setUserId(currentUser.getId());
		photo = AlbumService.addPhotoToAlbum(albumId, photo);
		
       if (photo != null){
    	   result = "{status: true, error: \"\", data: {id: \"" + photo.getId() + "\",smallImgUrl: \""
    			   + photo.getSmallImgUrl() + "\", middleImgUrl: \"" 
    			   + photo.getMiddleImgUrl() + "\", bigImgUrl: \"" 
    			   + photo.getBigImgUrl() + "\"} }";
       }else{
    	   result = "{status: false}";
       }
		
        return result;
	}
	
	@RequestMapping(value="/ajax/album/share/book/uploadphoto", method=RequestMethod.POST)
	@ResponseBody
	public String bookShareUploadPhoto(@RequestParam("file") MultipartFile file)
	{
		String result = "";

		Photo photo = AlbumService.uploadPhotoToServer(file, true);
		
       if (photo != null){
    	   result = "{status: true, error: \"\", data: {id: \"" + photo.getId() + "\",smallImgUrl: \""
    			   + photo.getSmallImgUrl() + "\", middleImgUrl: \"" 
    			   + photo.getMiddleImgUrl() + "\", bigImgUrl: \"" 
    			   + photo.getBigImgUrl() + "\"} }";
       }else{
    	   result = "{status: false}";
       }
		
        return result;
	}
}
