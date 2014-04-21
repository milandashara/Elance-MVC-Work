package com.coe.mxcommunity.album.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.coe.mxcommunity.entity.Album;
import com.coe.mxcommunity.entity.Photo;
import com.coe.mxcommunity.util.HibernateUtil;

public class AlbumDao {
	public static Album createAlbum(Album album){
		Album rAlbum = null;
		
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			
			session.save(album);
			rAlbum = album;
			
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
		
		return rAlbum;
	}
	
	public static Album getAlbum(Long albumId){
		Album album = null;
		
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			
			album = (Album)session.get(Album.class, albumId);
			
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
		
		return album;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Album> getAlbum(int ownerType, Long ownerId){
		List<Album> albumList = null;
		
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
					
			Criteria criteria = session.createCriteria(Album.class);
			criteria.add(Restrictions.eq("ownerType", ownerType));
			criteria.add(Restrictions.eq("ownerId", ownerId));
			
			albumList = (List<Album>)criteria.list();
			
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
		
		return albumList;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean groupAvatarAlbumExist(Long groupId){
		boolean bRet = false;
		
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(Album.class);
			criteria.add(Restrictions.eq("ownerType", 1));
			criteria.add(Restrictions.eq("ownerId", groupId));
			criteria.add(Restrictions.eq("albumType", 1));
			
			List<Album> list = (List<Album>)criteria.list();
			if (list.size() > 0){
				bRet = true;
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
		
		return bRet;
	}
	
	@SuppressWarnings("unchecked")
	public static Album getGroupAvatarAlbum(Long groupId){
		Album album = null;
		
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
					
			Criteria criteria = session.createCriteria(Album.class);
			criteria.add(Restrictions.eq("ownerType", 1));
			criteria.add(Restrictions.eq("ownerId", groupId));
			criteria.add(Restrictions.eq("albumType", 1));
			
			List<Album> list = (List<Album>)criteria.list();
			if (list.size() > 0){
				album = list.get(0);
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
		
		return album;
	}
	
	public static Photo addPhotoToAlbum(Long albumId, Photo photo){
		Photo rPhoto = null;
		
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			
			Album album = (Album)session.get(Album.class, albumId);
			if (album != null){
				album.getPhotos().add(photo);
				photo.setAlbum(album);
				session.save(album);
				
				rPhoto = photo;
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
		
		return rPhoto;
	}
	
	public static List<Photo> addPhotoListToAlbum(Long albumId, List<Photo> photos){
		List<Photo> rPhotos = null;
		
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			
			Album album = (Album)session.get(Album.class, albumId);
			if (album != null){
				for (int i = 0; i < photos.size(); i++){
					album.getPhotos().add(photos.get(i));
					photos.get(i).setAlbum(album);
				}
				
				session.save(album);
				
				rPhotos = photos;
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
		
		return rPhotos;
	}
	
	public static Photo getPhoto(Long photoId){
		Photo photo = null;
		
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			
			photo = (Photo)session.get(Photo.class, photoId);
			
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
		
		return photo;
	}
	
	public static boolean deletePhoto(Long photoId){
		boolean bRet = false;
		
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			
			Query query = session.createQuery("delete Photo c where c.id = :photoId");
			query.setLong("photoId", photoId);
			query.executeUpdate();
			
			bRet = true;
			
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
		
		return bRet;
	}
	
	public static boolean updatePhoto(Photo photo){
		boolean bRet = false;
		
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			
			session.update(photo);
			
			bRet = true;
			
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
		
		return bRet;
	}
}
