package com.coe.mxcommunity.mygroup.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.coe.mxcommunity.account.entity.User;
import com.coe.mxcommunity.mygroup.entity.GroupUser;
import com.coe.mxcommunity.mygroup.entity.MyGroup;
import com.coe.mxcommunity.util.HibernateUtil;
import com.coe.mxcommunity.view.entity.Pager;

public class MyGroupDao {
	
	public static Pager getGroupListByExample(int pageNumber, int pageSize, MyGroup myGroup){
		Pager pager = new Pager();
		pager.setPageNumber(pageNumber);
		pager.setPageSize(pageSize);
		List<MyGroup> groupList = new ArrayList<MyGroup>();
		
		Session session = HibernateUtil.getSession();
		try{
			Criteria criteria = session.createCriteria(MyGroup.class);
			criteria.add(Example.create(myGroup).enableLike().excludeNone().excludeZeroes());
			
			int size = ((Number)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
			criteria.setProjection(null);
			pager.setTotolCount(size);
			
			criteria.setFirstResult((pageNumber - 1) * pageSize);
			criteria.setMaxResults(pageSize);
			
			List list = HibernateUtil.initializeAndUnproxy(criteria.list());
			if (list.size() > 0){
				groupList = list;
			}		
		}
		catch(HibernateException he){
		}
		finally{
			session.close();
		}
		
		pager.setObject(groupList);
		
		return pager;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean isGroupExist(String groupName){
		boolean isExist = false;
		
		Session session = HibernateUtil.getSession();
		try{
			Criteria criteria = session.createCriteria(MyGroup.class);
			criteria.add(Restrictions.eq("groupName", groupName));
			
			List<MyGroup> groupList = (List<MyGroup>)criteria.list();
			if (groupList.size() > 0){
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
	
	public static MyGroup createGroup(MyGroup myGroup, User creator){
		MyGroup rGroup = null;
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			
			session.save(myGroup);
			
			session.update(creator);
			GroupUser groupUser = new GroupUser(myGroup, creator, false);	
			myGroup.getGroupUsers().add(groupUser);
			creator.getGroupUsers().add(groupUser);
			session.save(groupUser);
			tx.commit();
			
			rGroup = myGroup;
		}
		catch(HibernateException he){
			if (tx != null)
				tx.rollback();
		}
		finally{
			session.close();
		}
		
		return rGroup;
	}
	
	public static boolean addUserToGroup(Long groupId, Long userId, boolean isPending){
		boolean bRet = false;
		
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			
			GroupUser groupUser = new GroupUser();
			groupUser.getId().setGroupId(groupId);
			groupUser.getId().setUserId(userId);

			session.save(groupUser);
			
			bRet = true;
			tx.commit();
		}
		catch(HibernateException he){
			if (tx != null)
				tx.rollback();
		}
		finally{
			session.close();
		}
		
		return bRet;
	}
	
	public static boolean addUserToGroup(MyGroup myGroup, User user, boolean isPending){
		boolean bRet = false;
		
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			
			session.update(user);
			
			Criteria criteria = session.createCriteria(MyGroup.class);
			criteria.add(Example.create(myGroup).enableLike().excludeNone().excludeZeroes());
			criteria.setMaxResults(1);
			List list = criteria.list();
			if (list.size() > 0){
				MyGroup theGroup = (MyGroup)list.get(0);
				GroupUser groupUser = new GroupUser(theGroup, user, isPending);
				theGroup.getGroupUsers().add(groupUser);
				user.getGroupUsers().add(groupUser);
				session.save(groupUser);
				
				bRet = true;
			}
			
			tx.commit();
		}
		catch(HibernateException he){
			if (tx != null)
				tx.rollback();
		}
		finally{
			session.close();
		}
		
		return bRet;
	}
	
	public static MyGroup getGroupById(Long groupId){
		MyGroup myGroup = null;
		
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			
			myGroup = (MyGroup)session.get(MyGroup.class, groupId);
			
			tx.commit();
		}
		catch(HibernateException he){
		}
		finally{
			session.close();
		}
		
		return myGroup;
	}
	
//	@SuppressWarnings("unchecked")
//	public static List<MyGroup> getClassListBySchool(int schoolType, String schoolCode){
//		List<MyGroup> classList = null;
//		
//		Session session = HibernateUtil.getSession();
//		Transaction tx = null;
//		try{
//			tx = session.beginTransaction();
//			
//			Criteria criteria = session.createCriteria(MyGroup.class);
//			criteria.add(Restrictions.eq("schoolType", schoolType));
//			criteria.add(Restrictions.eq("schoolCode", schoolCode));
//			
//			classList = (List<MyGroup>)criteria.list();
//			
//			tx.commit();
//		}
//		catch(HibernateException he){
//		}
//		finally{
//			session.close();
//		}
//		
//		return classList;
//	}
	
	public static MyGroup getGroupByCode(int groupCode){
		MyGroup myGroup = null;
		
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(MyGroup.class);
			criteria.add(Restrictions.eq("code", groupCode));
			
			List list = criteria.list();
			if (list.size() > 0){
				myGroup = (MyGroup)list.get(0);
			}
			
			tx.commit();
		}
		catch(HibernateException he){
		}
		finally{
			session.close();
		}
		
		return myGroup;
	}
//	
//	public static Desk addDeskToClass(int classCode, Desk desk){
//		Desk rDesk = null;
//		
//		Session session = HibernateUtil.getSession();
//		Transaction tx = null;
//		try{
//			tx = session.beginTransaction();
//			
//			Criteria criteria = session.createCriteria(MyGroup.class);
//			criteria.add(Restrictions.eq("code", classCode));
//			
//			List list = criteria.list();
//			if (list.size() > 0){
//				MyGroup myGroup = (MyGroup)list.get(0);
//				myGroup.getDesks().add(desk);
//				desk.setMyClass(myGroup);
//				session.save(desk);
//				
//				rDesk = desk;
//			}
//			
//			tx.commit();
//		}
//		catch(HibernateException he){
//		}
//		finally{
//			session.close();
//		}
//		
//		return rDesk;
//	}
//	
//	public static boolean deleteDesk(Long deskId){
//		boolean bRet = false;
//		Session session = HibernateUtil.getSession();
//		Transaction tx = null;
//		try{
//			tx = session.beginTransaction();
//			
//			Desk desk = (Desk)session.get(Desk.class, deskId);
//			if (desk != null){
//				session.delete(desk);	
//			}
//			
//			tx.commit();
//		}
//		catch(HibernateException he){
//		}
//		finally{
//			session.close();
//		}
//		
//		return bRet;
//	}
//	
//	public static boolean updateDeskPositions(List<Desk> desks){
//		boolean bRet = false;
//		
//		Session session = HibernateUtil.getSession();
//		Transaction tx = null;
//		try{
//			tx = session.beginTransaction();
//			
//			String hqlUpdate = "update Desk desk set desk.x = :newX, desk.y = :newY where desk.id = :oldId";
//			
//			for (int i = 0; i < desks.size(); i++){
//				session.createQuery( hqlUpdate )
//				        .setInteger( "newX", desks.get(i).getX())
//				        .setInteger( "newY", desks.get(i).getY())
//				        .setLong( "oldId", desks.get(i).getId())
//				        .executeUpdate();	
//			}
//			
//			tx.commit();
//			bRet = true;
//		}
//		catch(HibernateException he){
//		}
//		finally{
//			session.close();
//		}
//		
//		return bRet;
//	}
//	
//	public static Desk bindDeskUser(Long deskId, User user){
//		Desk rDesk = null;
//		
//		Session session = HibernateUtil.getSession();
//		Transaction tx = null;
//		try{
//			tx = session.beginTransaction();
//			
//			session.update(user);
//			Desk desk = (Desk)session.get(Desk.class, deskId);
//			desk.setUser(user);
//			
//			session.update(desk);
//			tx.commit();
//			
//			if (user != null){
//				desk.setUserId(user.getId());
//				desk.setUserCode(user.getCode());
//				desk.setUserName(user.getName());
//				desk.setUserSex(user.getSex());
//				desk.setLastVisit(user.getLastVisit());
//			}
//			rDesk = desk;
//		}
//		catch(HibernateException he){
//			if (tx != null)
//				tx.rollback();
//		}
//		finally{
//			session.close();
//		}
//		
//		return rDesk;	
//	}
//	
//	public static boolean updateDeskPosition(int id, int x, int y){
//		boolean bRet = false;
//		
//		Session session = HibernateUtil.getSession();
//		Transaction tx = null;
//		try{
//			tx = session.beginTransaction();
//			
//			Desk desk = (Desk)session.load(Desk.class, id);
//			if (desk != null){
//				desk.setX(x);
//				desk.setY(y);
//				session.update(desk);
//			}
//			
//			tx.commit();
//			bRet = true;
//		}
//		catch(HibernateException he){
//		}
//		finally{
//			session.close();
//		}
//		
//		return bRet;
//	}
//	
//	public static List<Desk> getDeskList(int classCode){
//		List<Desk> deskList = new ArrayList<Desk>();
//		
//		Session session = HibernateUtil.getSession();
//		Transaction tx = null;
//		try{
//			tx = session.beginTransaction();
//			
//			Criteria criteria = session.createCriteria(MyGroup.class);
//			criteria.add(Restrictions.eq("code", classCode));
//			
//			List list = criteria.list();
//			if (list.size() > 0){
//				MyGroup myGroup = (MyGroup)list.get(0);
//				Set<Desk> deskSet = myGroup.getDesks();
//				
//				Desk desk = null;
//				User user = null;
//				Iterator itr = deskSet.iterator();
//				while (itr.hasNext()){
//					desk = (Desk)itr.next();
//					
//					desk = HibernateUtil.initializeAndUnproxy(desk);
//					user = desk.getUser();
//					if (user != null){
//						desk.setUserId(user.getId());
//						desk.setUserCode(user.getCode());
//						desk.setUserName(user.getName());
//						desk.setUserSex(user.getSex());
//						desk.setLastVisit(user.getLastVisit());
//					}
//					deskList.add(desk);
//				}
//			}
//			
//			tx.commit();
//		}
//		catch(HibernateException he){
//		}
//		finally{
//			session.close();
//		}
//		
//		return deskList;	
//	}
	
	public static List<User> getUserListByGroupCode(int groupCode, boolean isPending){
		List<User> userList = new ArrayList<User>();
		
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(MyGroup.class);
			criteria.add(Restrictions.eq("code", groupCode));
			
			List list = criteria.list();
			if (list.size() > 0){
				MyGroup myGroup = (MyGroup)list.get(0);
				Set<GroupUser> userSet = myGroup.getGroupUsers();
				
				GroupUser groupUser = null;
				User user  = null;
				Iterator itr = userSet.iterator();
				while (itr.hasNext()){
					groupUser = (GroupUser)itr.next();
					
					//获取已加入列表
					if (groupUser.isPending() == isPending){
						user = HibernateUtil.initializeAndUnproxy(groupUser.getUser());
						userList.add(user);
					}
				}
			}
			
			tx.commit();
		}
		catch(HibernateException he){
		}
		finally{
			session.close();
		}
		
		return userList;
	}
	
	public static List<MyGroup> getGroupListByUser(User user, boolean isPending){
		List<MyGroup> groupList = new ArrayList<MyGroup>();
		
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			
			session.update(user);
			Set<GroupUser> userSet = user.getGroupUsers();
			
			GroupUser groupUser = null;
			MyGroup myGroup  = null;
			Iterator itr = userSet.iterator();
			while (itr.hasNext()){
				groupUser = (GroupUser)itr.next();
				if (groupUser.isPending() == isPending){
					myGroup = HibernateUtil.initializeAndUnproxy(groupUser.getMyGroup());
					groupList.add(myGroup);	
				}
			}
			
			tx.commit();
		}
		catch(HibernateException he){
			System.out.println(he.toString());
		}
		finally{
			session.close();
		}
		
		return groupList;	
	}
	
	public static List<MyGroup> getGroupListByUserCode(int userCode, boolean isPending){
		List<MyGroup> groupList = new ArrayList<MyGroup>();
		
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("code", userCode));
			
			List list = criteria.list();
			if (list.size() > 0){
				User user = (User)list.get(0);
				Set<GroupUser> userSet = user.getGroupUsers();
				
				GroupUser groupUser = null;
				MyGroup myGroup  = null;
				Iterator itr = userSet.iterator();
				while (itr.hasNext()){
					groupUser = (GroupUser)itr.next();
					
					if (groupUser.isPending() == isPending){
						myGroup = HibernateUtil.initializeAndUnproxy(groupUser.getMyGroup());
						groupList.add(myGroup);						
					}
				}
			}
			
			tx.commit();
		}
		catch(HibernateException he){
		}
		finally{
			session.close();
		}
		
		return groupList;
	}
	
	public static boolean approvePending(Long groupId, Long userId){
		boolean bRet = false;
		
		Session session = HibernateUtil.getSession();
		
		Transaction tx = null;
		try{
			tx = session.beginTransaction();

			Query query = session.createQuery("update GroupUser c set c.pending = false where c.id.groupId = :groupId and c.id.userId = :userId");
			query.setLong("groupId", groupId);
			query.setLong("userId", userId);
			query.executeUpdate();
			
			bRet = true;
			
			tx.commit();
		}
		catch(HibernateException he){
			System.out.println(he.toString());
		}
		finally{
			session.close();
		}
		
		return bRet;
	}
	
	public static boolean refusePending(int groupCode, int userCode){
		boolean bRet = false;
		
		Session session = HibernateUtil.getSession();
		
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Query query = session.createQuery("select groupUser from User user, MyGroup mygroup, GroupUser groupUser where mygroup.code = :groupCode and user.code = :userCode and mygroup.id = groupUser.id.groupId and user.id = groupUser.id.userId and groupUser.pending = true");
			query.setInteger("groupCode", groupCode);
			query.setInteger("userCode", userCode);
			
			List list = query.list();
			Iterator it = list.iterator();
			while(it.hasNext()){
				GroupUser groupUser = (GroupUser)it.next();
				Query query1 = session.createQuery("delete GroupUser c where c.id.groupId = :groupId and c.id.userId = :userId");
				query1.setLong("groupId", groupUser.getId().getGroupId());
				query1.setLong("userId", groupUser.getId().getUserId());
				query1.executeUpdate();
			}
					
			tx.commit();
		}
		catch(HibernateException he){
			System.out.println(he.toString());
		}
		finally{
			session.close();
		}
		
		return bRet;
	}
}
