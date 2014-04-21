package com.coe.mxcommunity.photo.service;

public class PhotoService {
	public enum AvatarType{
		BIG,
		SMALL
	}
	
	public static String getAvatarUrl(String userCode, AvatarType type){
		String url = "photo\\avatar";
		
		switch (type){
		case BIG:
			url += "\\180";
			break;
		case SMALL:
			url += "\\50";
			break;
		}
		
		return url;
	}
}
