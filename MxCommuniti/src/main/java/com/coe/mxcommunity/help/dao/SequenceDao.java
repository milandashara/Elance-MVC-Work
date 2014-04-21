package com.coe.mxcommunity.help.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.coe.mxcommunity.help.entity.Sequence;
import com.coe.mxcommunity.util.HibernateUtil;

public class SequenceDao {
	public static boolean addSequence(Sequence seq){
		boolean bRet = false;
		
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();		
			session.save(seq);
			tx.commit();
			
			bRet = true;
		}
		catch(HibernateException he){
			if (tx != null)
				tx.rollback();
			throw he;
		}
		finally{
			session.close();
		}
		
		return bRet;
	}
	
	public static boolean updateSequence(Sequence seq){
		boolean bRet = false;
		
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();		
			session.update(seq);
			tx.commit();
			
			bRet = true;
		}
		catch(HibernateException he){
			if (tx != null)
				tx.rollback();
			throw he;
		}
		finally{
			session.close();
		}
		
		return bRet;
	}
	
	@SuppressWarnings("unchecked")
	public static Sequence getSequence(int type){
		Sequence seq = null;
		
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(Sequence.class);
			criteria.add(Restrictions.eq("seqType", type));
			
			List<Sequence> list = (List<Sequence>)criteria.list();
			if (list.size() > 0){
				seq = list.get(0);
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
		
		return seq;
	}
}
