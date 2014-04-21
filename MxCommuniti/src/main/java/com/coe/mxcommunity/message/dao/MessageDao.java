package com.coe.mxcommunity.message.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.coe.mxcommunity.message.entity.Message;
import com.coe.mxcommunity.util.HibernateUtil;

public class MessageDao {
	public static Message createMessage(Message message){
		Message rMessage = null;
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			
			session.save(message);
			tx.commit();
			
			rMessage = message;
		}
		catch(HibernateException he){
			if (tx != null)
				tx.rollback();
		}
		finally{
			session.close();
		}
		
		return rMessage;
	}
	
	public static List<Message> getMessageList(Long groupId, Date startTime, int pageNumber, int pageSize){
		List<Message> msgList = new ArrayList<Message>();
		
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			
			Query query = session.createQuery("select distinct msg from Message msg, User user, MyGroup mygroup, GroupUser clUser where " 
					+ "((msg.senderType = 2 and msg.receiverType = 1 and (msg.receiverId = :groupId or msg.receiverId = 0)) or "
					+ "(msg.senderType = 1 and msg.senderId = :groupId) or "
					+ "(msg.senderType = 0 and msg.receiverType = 1 and (msg.receiverId = :groupId or msg.receiverId = 0) and " 
						+ "mygroup.id = :groupId and clUser.id.groupId = mygroup.id and user.id = clUser.id.userId and msg.senderId = user.id" 
					+ ")) and mygroup.id = :groupId and msg.createTime >= mygroup.createTime and "
					+ "msg.createTime <= :startTime " 
					+ "order by msg.createTime desc");
			query.setLong("groupId", groupId);
			query.setTimestamp("startTime", new Timestamp(startTime.getTime()));
			
			if (pageNumber > 0){
				query.setFirstResult((pageNumber - 1) * pageSize);
			}
			
			if (pageSize > 0){
				query.setMaxResults(pageSize);
			}
			
			List list = query.list();
			
			Iterator it = list.iterator();
			while(it.hasNext()){
				Message msg =  (Message)it.next();
				msgList.add(msg);
			}				
			
			tx.commit();
		}
		catch(Exception he){
			System.out.println(he.toString());
		}
		finally{
			session.close();
		}
		
		return msgList;
	}
	
	public static List<Message> getMyMessageList(Long userId, Date startTime, int pageNumber, int pageSize){
		List<Message> msgList = new ArrayList<Message>();
		
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			
			Query query = session.createQuery("select distinct msg from Message msg where " 
					+ "(msg.receiverType = 0 and msg.receiverId = :userId) and "
					+ "msg.createTime <= :startTime " 
					+ "order by msg.createTime desc");
			query.setLong("userId", userId);
			query.setTimestamp("startTime", new Timestamp(startTime.getTime()));
			
			if (pageNumber > 0){
				query.setFirstResult((pageNumber - 1) * pageSize);
			}
			
			if (pageSize > 0){
				query.setMaxResults(pageSize);
			}
			
			List list = query.list();
			
			Iterator it = list.iterator();
			while(it.hasNext()){
				Message msg =  (Message)it.next();
				msgList.add(msg);
			}				
			
			tx.commit();
		}
		catch(Exception he){
			System.out.println(he.toString());
		}
		finally{
			session.close();
		}
		
		return msgList;
	}
}
