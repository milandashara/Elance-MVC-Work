package com.coe.mxcommunity.ftp;

import java.io.InputStream;
import java.util.Map;

import com.coe.mxcommunity.configuration.Configuration;
import com.coe.mxcommunity.util.FtpUtil;

public class ImageFTP {
	public static boolean UploadFile(String dir, String fileName, InputStream input){
		boolean bRet = false;
		bRet = FtpUtil.uploadFile(Configuration.getFtpImageIP(), 
				Configuration.getFtpImagePort(),
				Configuration.getFtpImageUser(),
				Configuration.getFtpImagePassword(),
				dir, fileName, input); 
		
		return bRet;
	}
	
	public static boolean moveFile(String source, String dest){
		boolean bRet = false;
		
		bRet = FtpUtil.moveFile(Configuration.getFtpImageIP(), 
				Configuration.getFtpImagePort(),
				Configuration.getFtpImageUser(),
				Configuration.getFtpImagePassword(),
				source, dest); 
		
		return bRet;
	}
	
	public static boolean moveFiles(Map<String, String> fileMap){
		boolean bRet = false;
		
		bRet = FtpUtil.moveFiles(Configuration.getFtpImageIP(), 
				Configuration.getFtpImagePort(),
				Configuration.getFtpImageUser(),
				Configuration.getFtpImagePassword(),
				fileMap);
		
		return bRet;
	}
}
