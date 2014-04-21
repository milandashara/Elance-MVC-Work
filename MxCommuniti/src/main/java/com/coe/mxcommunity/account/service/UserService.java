package com.coe.mxcommunity.account.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.coe.mxcommunity.account.dao.UserDao;
import com.coe.mxcommunity.account.entity.User;
import com.coe.mxcommunity.configuration.Configuration;
import com.coe.mxcommunity.ftp.ImageFTP;
import com.coe.mxcommunity.help.CodeGenerator;
import com.coe.mxcommunity.help.entity.ImageType;
import com.coe.mxcommunity.util.ImageUtil;

public class UserService {	
		
	public static boolean updateUser(User user){
		return UserDao.updateUser(user);
	}
	
	public static boolean updateUserAvatar(MultipartFile file, User user){
		boolean bRet = false;
		
		String bigDir = "photo/avatar/180"; 
		String smallDir = "photo/avatar/50";
		String bigFile = CodeGenerator.generateImageCode(ImageType.AVATAR_BIG) + ".jpg";
		String smallFile = CodeGenerator.generateImageCode(ImageType.AVATAR_SMALL) + ".jpg";
		
    	try{
    		InputStream smallInput = ImageUtil.cutZoomImageToJPG(file.getInputStream(), 50, 50);
    		InputStream bigInput = ImageUtil.cutZoomImageToJPG(file.getInputStream(), 180, 180);
    		
    		ImageFTP.UploadFile(bigDir, bigFile, bigInput);
    		ImageFTP.UploadFile(smallDir, smallFile, smallInput);
    		
    		user.setBigAvatar(Configuration.getImageHost() + "/" + bigDir + "/" + bigFile);
    		user.setSmallAvatar(Configuration.getImageHost() + "/" + smallDir + "/" + smallFile);
    		UserService.updateUser(user);	
    		
    		bRet = true;
    	}
    	catch (Exception e){
    	}

		
		return bRet;
	}
}
