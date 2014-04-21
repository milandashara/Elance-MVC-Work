package com.coe.mxcommunity.account.dao;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.coe.mxcommunity.account.entity.User;
import com.coe.mxcommunity.mygroup.entity.GroupUser;
import com.coe.mxcommunity.mygroup.entity.MyGroup;
import com.coe.mxcommunity.util.HibernateUtil;

public class UserDao {
	@SuppressWarnings("unchecked")
	public static boolean emailExist(String email){
		boolean isExist = false;
		
		Session session = HibernateUtil.getSession();
		try{
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("email", email));

			List<User> userList = (List<User>)criteria.list();
			if (userList.size() > 0){
				isExist = true;
			}
			
		}
		catch(HibernateException he){
		}
		finally{
			session.close();
		}
		
		return isExist;
	}
	
	public static User addUser(User user){
		User rUser = null;
		
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.save(user);
			
			rUser = user;
			
			tx.commit();
		}
		catch(HibernateException he){
			if (tx != null)
				tx.rollback();
			throw he;
		}
		finally{
			session.close();
		}
		
		return rUser;
	}
	
	public static List<MyGroup> getUserGroupList(long id){
		List<MyGroup> groupList = new ArrayList<MyGroup>();
		
		User user = null;
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			user = (User)session.get(User.class, id);
			Set<GroupUser> groupUserSet = user.getGroupUsers();
			Iterator<GroupUser> itr = groupUserSet.iterator();
			while(itr.hasNext()){
				GroupUser groupUser = (GroupUser)itr.next();
				groupList.add(groupUser.getMyGroup());
			}
			
			tx.commit();
		}
		catch(HibernateException he){
			if (tx != null)
				tx.rollback();
			throw he;
		}
		finally{
			session.close();
		}
		
		return groupList;	
	}
	
	public static User getUser(long id){
		User user = null;
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			user = (User)session.get(User.class, id);
			tx.commit();
		}
		catch(HibernateException he){
			if (tx != null)
				tx.rollback();
			throw he;
		}
		finally{
			session.close();
		}
		
		return user;
	}
	
	@SuppressWarnings("unchecked")
	public static User getUserByEmail(String email){
		User user = null;
		Session session = HibernateUtil.getSession();
		try{
			
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("email", email));

			List<User> userList = (List<User>)criteria.list();
			if (userList.size() > 0){
				user = (User)userList.get(0);
			}
			
		}
		catch(HibernateException he){
			throw he;
		}
		finally{
			session.close();
		}
		
		return user;
	}
	
	public static boolean updateUser(User user){
		boolean bRet = false;
		
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			
			session.update(user);
			
			tx.commit();
			
			bRet = true;
		}
		catch(HibernateException he){
		}
		finally{
			session.close();
		}
		
		return bRet;
	}
	
	@SuppressWarnings("unchecked")
	public static List<User> getUserListByType(int type){
		List<User> userList = null;
		Session session = HibernateUtil.getSession();
		try{
			
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("type", type));

			userList = (List<User>)criteria.list();
			
		}
		catch(HibernateException he){
			throw he;
		}
		finally{
			session.close();
		}
		
		return userList;
	}
}
