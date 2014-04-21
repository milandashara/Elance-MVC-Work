package com.coe.mxcommunity.message.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.coe.mxcommunity.account.dao.UserDao;
import com.coe.mxcommunity.account.entity.User;
import com.coe.mxcommunity.album.service.AlbumService;
import com.coe.mxcommunity.entity.Album;
import com.coe.mxcommunity.entity.Photo;
import com.coe.mxcommunity.message.controller.MessageRequest;
import com.coe.mxcommunity.message.dao.MessageDao;
import com.coe.mxcommunity.message.entity.Message;

public class MessageService {
	
	public static boolean sendMessageToUser(Long userId, String content){
		boolean bRet = false;
		
		Message msg = new Message();
		msg.setSenderType(2);
		msg.setReceiverType(0);
		msg.setReceiverId(userId);		
		msg.setContent(content);		
		msg.setCreateTime(new Timestamp((new Date()).getTime()));
		
		msg = MessageDao.createMessage(msg);
		if (msg != null){
			bRet = true;
		}
		
		return bRet;		
	}
	
	public static boolean sendMessageToGroup(Long groupId, String content){
		boolean bRet = false;
		
		Message msg = new Message();
		msg.setSenderType(1);
		msg.setSenderId(groupId);
		msg.setReceiverType(1);
		msg.setReceiverId(groupId);		
		msg.setContent(content);		
		msg.setCreateTime(new Timestamp((new Date()).getTime()));
		
		msg = MessageDao.createMessage(msg);
		if (msg != null){
			bRet = true;
		}
		
		return bRet;
	}
	
	public static Message publishUserMessage(MessageRequest msgReq, Long userId){		
		Message msg = new Message();
		msg.setSenderType(0);
		msg.setSenderId(userId);
		msg.setReceiverType(1);
		msg.setReceiverId(msgReq.getGroupId());		
		msg.setContent(msgReq.getContent());		
		msg.setCreateTime(new Timestamp((new Date()).getTime()));
		
		msg = MessageDao.createMessage(msg);
		if (msg != null){
			List<Photo> photoList = msgReq.getPhotos();
			if (photoList != null){
				Album album = AlbumService.createMsgAlbum(msg.getId());
				Photo photo = null;
				if (album != null){
					for (int i = 0; i < photoList.size(); i++){
						photo = photoList.get(i);
						photo = AlbumService.movePhotoFileFromTemp(photo);
						if (photo != null){
							photo = AlbumService.addPhotoToAlbum(album.getId(), photo);
							if (photo != null){
								album.getPhotos().add(photo);
							}
						}
					}
					
					msg.setAlbumId(album.getId());
					msg.setAlbum(album);
				}
			}
			
			User user = UserDao.getUser(msg.getSenderId());
			if (user != null){
				msg.setSenderName(user.getName());
				msg.setSenderAvatar(user.getSmallAvatar());
			}
		}
		
		return msg;
	}
	
	public static List<Message> getMessageList(Long groupId, Date startTime, int pageNumber, int pageSize){
		if (startTime == null){
			startTime = new Date();
		}
		List<Message> list = MessageDao.getMessageList(groupId, startTime, pageNumber, pageSize);
		Message msg = null;
		Album album = null;
		for (int i = 0; i < list.size(); i++){
			msg = list.get(i);	
			
			if (msg.getSenderType() == 0){
				User user = UserDao.getUser(msg.getSenderId());
				if (user != null){
					msg.setSenderName(user.getName());
					msg.setSenderAvatar(user.getSmallAvatar());
				}
			}
			else if (msg.getSenderType() == 1){
				msg.setSenderName("班级系统");
				msg.setSenderAvatar("images/avatar/avatar_group_icon.png");
			}
			
			album = AlbumService.getMsgAlbum(msg.getId());
			if (album != null){
				msg.setAlbum(album);
			}
		}
		return list; 
	}
	
	public static List<Message> getMyMessage(Long userId, Date startTime, int pageNumber, int pageSize){
		if (startTime == null){
			startTime = new Date();
		}
		
		List<Message> list = MessageDao.getMyMessageList(userId, startTime, pageNumber, pageSize);
		Message msg = null;
		Album album = null;
		for (int i = 0; i < list.size(); i++){
			msg = list.get(i);	
			
			if (msg.getSenderType() == 0){
				User user = UserDao.getUser(msg.getSenderId());
				if (user != null){
					msg.setSenderName(user.getName());
					msg.setSenderAvatar(user.getSmallAvatar());
				}
			}
			else if (msg.getSenderType() == 1){
				msg.setSenderName("班级系统");
				msg.setSenderAvatar("images/avatar/avatar_group_icon.png");
			}
			else if(msg.getSenderType() == 2){
				msg.setSenderName("TOWER");
				msg.setSenderAvatar("images/avatar/avatar_tower_icon.png");			
			}
			
			album = AlbumService.getMsgAlbum(msg.getId());
			if (album != null){
				msg.setAlbum(album);
			}
		}
		return list; 
	}
}
