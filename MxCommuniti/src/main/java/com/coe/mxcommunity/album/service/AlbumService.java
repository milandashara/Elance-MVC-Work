package com.coe.mxcommunity.album.service;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.coe.mxcommunity.album.dao.AlbumDao;
import com.coe.mxcommunity.configuration.Configuration;
import com.coe.mxcommunity.entity.Album;
import com.coe.mxcommunity.entity.Photo;
import com.coe.mxcommunity.ftp.ImageFTP;
import com.coe.mxcommunity.help.CodeGenerator;
import com.coe.mxcommunity.help.entity.ImageType;
import com.coe.mxcommunity.util.ImageUtil;

public class AlbumService {
	public final static String ALBUM_PHOTO_TEMP = "albumtemp"; 
	public final static String ALBUM_PHOTO_80_PATH = "photo/album/80";
	public final static String ALBUM_PHOTO_200_PATH = "photo/album/200";
	public final static String ALBUM_PHOTO_690_PATH = "photo/album/690";
	
	public static Photo uploadPhotoToServer(MultipartFile file){
		return AlbumService.uploadPhotoToServer(file, false);
	}
	
	public static Photo uploadPhotoToServer(MultipartFile file, boolean isTempDir){
		Photo photo = null;
		
		String smallImgDir;
		String middleImgDir;
		String bigImgDir;
		if (isTempDir){
			smallImgDir = ALBUM_PHOTO_TEMP + "/" + ALBUM_PHOTO_80_PATH;
			middleImgDir = ALBUM_PHOTO_TEMP + "/" + ALBUM_PHOTO_200_PATH;
			bigImgDir = ALBUM_PHOTO_TEMP + "/" + ALBUM_PHOTO_690_PATH;	
		}else{
			smallImgDir = ALBUM_PHOTO_80_PATH;
			middleImgDir = ALBUM_PHOTO_200_PATH;
			bigImgDir = ALBUM_PHOTO_690_PATH;			
		}
		
		String fileName = CodeGenerator.generateImageCode(ImageType.ALBUM_PHOTO) + ".jpg";
		
    	try{
    		InputStream smallInput = ImageUtil.cutZoomImageToJPG(file.getInputStream(), 80, 80);
    		InputStream middleInput = ImageUtil.zoomImageByWidthToJPG(file.getInputStream(), 200, false);
    		InputStream bigInput = ImageUtil.zoomImageByWidthToJPG(file.getInputStream(), 690, false);
    		
    		ImageFTP.UploadFile(smallImgDir, fileName, smallInput);
    		ImageFTP.UploadFile(middleImgDir, fileName, middleInput);
    		ImageFTP.UploadFile(bigImgDir, fileName, bigInput);
    		
    		photo = new Photo();
    		photo.setSmallImgUrl(Configuration.getImageHost() + "/" + smallImgDir + "/" + fileName);
    		photo.setMiddleImgUrl(Configuration.getImageHost() + "/" + middleImgDir + "/" + fileName);
    		photo.setBigImgUrl(Configuration.getImageHost() + "/" + bigImgDir + "/" + fileName);
    		photo.setCreateTime(new Timestamp((new Date()).getTime()));
    	}
    	catch (Exception e){
    	}
		
		return photo;
	}
	
	public static Photo movePhotoFileFromTemp(Photo photo){
		Photo rPhoto = null;
		
		if (photo != null){
			String smallSource = photo.getSmallImgUrl();
			String middleSource = photo.getMiddleImgUrl();
			String bigSource = photo.getBigImgUrl();
			String smallDest = "";
			String middleDest = "";
			String bigDest = "";
			
			String tempPath = ALBUM_PHOTO_TEMP;
			int index = smallSource.indexOf(tempPath);
			if (index != -1){
				smallDest = smallSource.substring(index + tempPath.length() + 1);
				smallSource = smallSource.substring(index);
			}
			
			index = middleSource.indexOf(tempPath);
			if (index != -1){
				middleDest = middleSource.substring(index + tempPath.length() + 1);
				middleSource = middleSource.substring(index);
			}
			
			index = bigSource.indexOf(tempPath);
			if (index != -1){
				bigDest = bigSource.substring(index + tempPath.length() + 1);
				bigSource = bigSource.substring(index);
			}
			
			Map<String, String> fileMap = new HashMap<String, String>();
			if (!smallDest.isEmpty()){
				fileMap.put(smallSource, smallDest);	
			}else{
				
			}

			if (!middleDest.isEmpty()){
				fileMap.put(middleSource, middleDest);
			}
			
			if (!bigDest.isEmpty()){
				fileMap.put(bigSource, bigDest);
			}
			
			
			ImageFTP.moveFiles(fileMap);
					
			rPhoto = new Photo();
			rPhoto.setSmallImgUrl(Configuration.getImageHost() + "/" + smallDest);
			rPhoto.setMiddleImgUrl(Configuration.getImageHost() + "/" + middleDest);
			rPhoto.setBigImgUrl(Configuration.getImageHost() + "/" + bigDest);
			rPhoto.setCreateTime(new Timestamp((new Date()).getTime()));			
		}
		
		return rPhoto;
	}
	
	public static Album getAlbum(Long albumId){
		return AlbumDao.getAlbum(albumId);
	}
	
	public static Album createMsgAlbum(Long msgId){
		return AlbumService.createMsgAlbum(msgId, "");
	}
	
	public static Album createMsgAlbum(Long msgId, String albumName){
		Album album = new Album();
		album.setOwnerType(2);
		album.setOwnerId(msgId);
		album.setAlbumName(albumName);
		album.setCreateTime(new Timestamp((new Date()).getTime()));
		
		return AlbumDao.createAlbum(album);
	}
	
	public static Album getMsgAlbum(Long msgId){
		Album album = null;
		
		List<Album> albumList = AlbumDao.getAlbum(2, msgId);
		if (albumList != null && albumList.size() > 0){
			album = albumList.get(0);
		}
		
		return album;
	}
	
	public static Album getActivityAlbum(Long activityId){
		Album album = null;
		
		List<Album> albumList = AlbumDao.getAlbum(4, activityId);
		if (albumList != null && albumList.size() > 0){
			album = albumList.get(0);
		}
		
		return album;
	}
	
	public static Photo addPhotoToAlbum(Long albumId, Photo photo){
		return AlbumDao.addPhotoToAlbum(albumId, photo);
	}
	
	public static boolean albumExist(Long groupId){
		return AlbumDao.groupAvatarAlbumExist(groupId);
	}
	
	public static Album getGroupAvatarAlbum(Long groupId){
		Album album = AlbumDao.getGroupAvatarAlbum(groupId);
		if (album == null){
			album = new Album();
			album.setOwnerType(1);
			album.setOwnerId(groupId);
			album.setAlbumType(1);
			album = AlbumDao.createAlbum(album);
		}
		
		return album;
	}
	
	public static Photo getGroupAvatar(Long groupId){
		Photo photo = null;
		
		Album album = AlbumDao.getGroupAvatarAlbum(groupId);
		if (album != null){
			String mark;
			Set<Photo> photos = album.getPhotos();
			Iterator<Photo> itr = photos.iterator();
			while(itr.hasNext()){
				Photo tempPhoto = (Photo)itr.next();
				mark = tempPhoto.getMark();
				if (mark != null && mark.equalsIgnoreCase("avatar")){
					photo = tempPhoto;
					break;
				}
			}
		}
		
		return photo;
	}
	
	public static boolean setGroupAvatar(Long groupId, Long photoId){
		boolean bRet = false;
		
		Photo photo = null;		
		photo = AlbumService.getGroupAvatar(groupId);
		if (photo != null){
			photo.setMark("");
			AlbumDao.updatePhoto(photo);
		}
		
		photo = AlbumDao.getPhoto(photoId);
		if (photo != null){
			photo.setMark("avatar");
			AlbumDao.updatePhoto(photo);
			bRet = true;
		}
		
		return bRet;
	}
	
//	public static Photo addPhotoToAlbum(Long albumId, MultipartFile file, Long userId){
//		Photo rPhoto = null;
//		
//		String smallImgDir = "photo/album/80";
//		String middleImgDir = "photo/album/200";
//		String bigImgDir = "photo/album/690"; 
//		String fileName = CodeGenerator.generateImageCode(ImageType.ALBUM_PHOTO) + ".jpg";
//		
//    	try{
//    		InputStream smallInput = ImageUtil.zoomImageByWidthToJPG(file.getInputStream(), 80, false);
//    		InputStream middleInput = ImageUtil.zoomImageByWidthToJPG(file.getInputStream(), 200, false);
//    		InputStream bigInput = ImageUtil.zoomImageByWidthToJPG(file.getInputStream(), 690, false);
//    		
//    		ImageFTP.UploadFile(smallImgDir, fileName, smallInput);
//    		ImageFTP.UploadFile(middleImgDir, fileName, middleInput);
//    		ImageFTP.UploadFile(bigImgDir, fileName, bigInput);
//    		
//    		Photo photo = new Photo();
//    		photo.setUserId(userId);
//    		photo.setSmallImgUrl(Configuration.getImageHost() + "/" + smallImgDir + "/" + fileName);
//    		photo.setMiddleImgUrl(Configuration.getImageHost() + "/" + middleImgDir + "/" + fileName);
//    		photo.setBigImgUrl(Configuration.getImageHost() + "/" + bigImgDir + "/" + fileName);
//    		photo.setCreateTime(new Timestamp((new Date()).getTime()));
//    		
//    		rPhoto = AlbumDao.addPhotoToAlbum(albumId, photo);
//    	}
//    	catch (Exception e){
//    	}
//		
//		return rPhoto;
//	}
	
	
//	public static Photo uploadImageToTemp(MultipartFile file){
//		Photo rPhoto = null;
//		
//		String smallImgDir = Configuration.PATH_IMAGE_TEMP + "/photo/album/80";
//		String middleImgDir = Configuration.PATH_IMAGE_TEMP + "/photo/album/200";
//		String bigImgDir = Configuration.PATH_IMAGE_TEMP + "/photo/album/690"; 
//		String fileName = CodeGenerator.generateImageCode(ImageType.ALBUM_PHOTO) + ".jpg";
//		
//    	try{
//    		InputStream smallInput = ImageUtil.zoomImageByWidthToJPG(file.getInputStream(), 80, false);
//    		InputStream middleInput = ImageUtil.zoomImageByWidthToJPG(file.getInputStream(), 200, false);
//    		InputStream bigInput = ImageUtil.zoomImageByWidthToJPG(file.getInputStream(), 690, false);
//    		
//    		ImageFTP.UploadFile(smallImgDir, fileName, smallInput);
//    		ImageFTP.UploadFile(middleImgDir, fileName, middleInput);
//    		ImageFTP.UploadFile(bigImgDir, fileName, bigInput);
//    		
//    		Photo photo = new Photo();
//    		photo.setSmallImgUrl(Configuration.getImageHost() + "/" + smallImgDir + "/" + fileName);
//    		photo.setMiddleImgUrl(Configuration.getImageHost() + "/" + middleImgDir + "/" + fileName);
//    		photo.setBigImgUrl(Configuration.getImageHost() + "/" + bigImgDir + "/" + fileName);
//
//    		rPhoto = photo;
//    	}
//    	catch (Exception e){
//    	}
//		
//		return rPhoto;
//	}
}
