package com.coe.mxcommunity.help;

import java.util.Random;

import com.coe.mxcommunity.help.dao.SequenceDao;
import com.coe.mxcommunity.help.entity.GroupType;
import com.coe.mxcommunity.help.entity.CodeType;
import com.coe.mxcommunity.help.entity.ImageType;
import com.coe.mxcommunity.help.entity.Sequence;
import com.coe.mxcommunity.help.entity.UserType;

public class CodeGenerator {
	
	public static int generateGroupCode(GroupType type, int year){
		int code = 0;
		Sequence seq = null;
		
		switch (type){
		case SPORT:
			seq = SequenceDao.getSequence(CodeType.GROUP_SPORT);
			if(seq == null){
				seq = new Sequence();
				seq.setSeqType(CodeType.GROUP_SPORT);
				seq.setSeqNum(700010000 + (year % 100) * 1000000);
				
				SequenceDao.addSequence(seq);
			}else{
				if (seq.getSeqNum() % 1000000 == 999999){
					seq.setSeqNum(800010000 + (year % 100) * 1000000);
				}else{
					seq.setSeqNum(seq.getSeqNum() + 1);			
				}

				SequenceDao.updateSequence(seq);
			}
			break;
		case MUSIC:
			seq = SequenceDao.getSequence(CodeType.GROUP_MUSIC);
			if(seq == null){
				seq = new Sequence();
				seq.setSeqType(CodeType.GROUP_MUSIC);
				seq.setSeqNum(500010000 + (year % 100) * 1000000);
				
				SequenceDao.addSequence(seq);
			}else{
				if (seq.getSeqNum() % 1000000 == 999999){
					seq.setSeqNum(600010000 + (year % 100) * 1000000);
				}else{
					seq.setSeqNum(seq.getSeqNum() + 1);			
				}
	
				SequenceDao.updateSequence(seq);
			}
			break;
		case FOOD:
			seq = SequenceDao.getSequence(CodeType.GROUP_FOOD);
			if(seq == null){
				seq = new Sequence();
				seq.setSeqType(CodeType.GROUP_FOOD);
				seq.setSeqNum(300010000 + (year % 100) * 1000000);
				
				SequenceDao.addSequence(seq);
			}else{
				if (seq.getSeqNum() % 1000000 == 999999){
					seq.setSeqNum(400010000 + (year % 100) * 1000000);
				}else{
					seq.setSeqNum(seq.getSeqNum() + 1);	
				}
				
				SequenceDao.updateSequence(seq);
			}
			break;			
		case TRAVEL:
			seq = SequenceDao.getSequence(CodeType.GROUP_TRAVEL);
			if(seq == null){
				seq = new Sequence();
				seq.setSeqType(CodeType.GROUP_TRAVEL);
				seq.setSeqNum(100010000 + (year % 100) * 1000000);
				
				SequenceDao.addSequence(seq);
			}else{
				if (seq.getSeqNum() % 1000000 == 999999){
					seq.setSeqNum(200010000 + (year % 100) * 1000000);
				}else{
					seq.setSeqNum(seq.getSeqNum() + 1);			
				}

				SequenceDao.updateSequence(seq);
			}
			break;
		case MODEL:
			seq = SequenceDao.getSequence(CodeType.GROUP_MODEL);
			if(seq == null){
				seq = new Sequence();
				seq.setSeqType(CodeType.GROUP_MODEL);
				seq.setSeqNum(900010000 + (year % 100) * 1000000);
				
				SequenceDao.addSequence(seq);
			}else{
				if (seq.getSeqNum() % 1000000 == 999999){
					seq.setSeqNum(1000010000 + (year % 100) * 1000000);
				}else{
					seq.setSeqNum(seq.getSeqNum() + 1);			
				}

				SequenceDao.updateSequence(seq);
			}
		}
		
		if (seq != null){
			code = seq.getSeqNum();	
		}
		
		return code;
	}
	
	public static int generateUserCode(UserType type){
		int code = 0;
		Sequence seq = null;
		
		switch (type){
		case MALE:
			seq = SequenceDao.getSequence(CodeType.USER_MALE_CODE);
			if(seq == null){
				seq = new Sequence();
				seq.setSeqType(CodeType.USER_MALE_CODE);
				seq.setSeqNum(100010000);
				
				SequenceDao.addSequence(seq);
			}else{
				if (seq.getSeqNum() == 199999999 
					|| seq.getSeqNum() == 399999999
					|| seq.getSeqNum() == 599999999)
				{
					seq.setSeqNum(seq.getSeqNum() + 100010001);
					
				}else{
					seq.setSeqNum(seq.getSeqNum() + 1);	
				}
				
				SequenceDao.updateSequence(seq);
			}			
			break;
		case FEMALE:
			seq = SequenceDao.getSequence(CodeType.USER_FEMALE_CODE);
			if(seq == null){
				seq = new Sequence();
				seq.setSeqType(CodeType.USER_FEMALE_CODE);
				seq.setSeqNum(200010000);
				
				SequenceDao.addSequence(seq);
			}else{
				if (seq.getSeqNum() == 299999999 
					|| seq.getSeqNum() == 499999999
					|| seq.getSeqNum() == 699999999)
				{
					seq.setSeqNum(seq.getSeqNum() + 100010001);
					
				}else{
					seq.setSeqNum(seq.getSeqNum() + 1);
				}
				
				SequenceDao.updateSequence(seq);
			}
			break;
		}
		
		if (seq != null){
			code = seq.getSeqNum();	
		}
		
		Random random = new Random();
		code = code * 1000 + random.nextInt() % 1000;
		
		return code;
	}
	
	public static int generateImageCode(ImageType type){
		int code = 0;
		Sequence seq = null;

		switch (type){
		case AVATAR_BIG:
			seq = SequenceDao.getSequence(CodeType.IMAGE_AVATAR_BIG);
			if(seq == null){
				seq = new Sequence();
				seq.setSeqType(CodeType.IMAGE_AVATAR_BIG);
				seq.setSeqNum(10000001);
				
				SequenceDao.addSequence(seq);
			}else{
				seq.setSeqNum(seq.getSeqNum() + 1);	
				SequenceDao.updateSequence(seq);
			}			
			break;
		case AVATAR_SMALL:
			seq = SequenceDao.getSequence(CodeType.IMAGE_AVATAR_SMALL);
			if(seq == null){
				seq = new Sequence();
				seq.setSeqType(CodeType.IMAGE_AVATAR_SMALL);
				seq.setSeqNum(10000001);
				
				SequenceDao.addSequence(seq);
			}else{
				seq.setSeqNum(seq.getSeqNum() + 1);	
				SequenceDao.updateSequence(seq);
			}	
			break;
		case IMAGE_MESSAGE:
			seq = SequenceDao.getSequence(CodeType.IMAGE_MESSAGE);
			if(seq == null){
				seq = new Sequence();
				seq.setSeqType(CodeType.IMAGE_MESSAGE);
				seq.setSeqNum(10000001);
				
				SequenceDao.addSequence(seq);
			}else{
				seq.setSeqNum(seq.getSeqNum() + 1);	
				SequenceDao.updateSequence(seq);
			}	
			break;
		case ALBUM_PHOTO:
			seq = SequenceDao.getSequence(CodeType.ALBUM_PHOTO);
			if(seq == null){
				seq = new Sequence();
				seq.setSeqType(CodeType.ALBUM_PHOTO);
				seq.setSeqNum(10000001);
				
				SequenceDao.addSequence(seq);
			}else{
				seq.setSeqNum(seq.getSeqNum() + 1);	
				SequenceDao.updateSequence(seq);
			}
			break;
		}
		
		if (seq != null){
			code = seq.getSeqNum();	
		}
		
		Random random = new Random();
		code = code * 1000 + random.nextInt() % 1000;
		
		return code;
	}
}
