package com.framework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.framework.dao.NoticeDao;
import com.framework.domain.NoticeInfo;

@Service
public class NoticeService {

	@Autowired
	NoticeDao noticeDao;
	
	public boolean hasMatchNoticeInfo(int deviceId){
		return noticeDao.getMatchCount(deviceId)>0;
	}
	
	public void addNoticeInfo(NoticeInfo noticeInfo){
		noticeDao.insertNoticeInfo(noticeInfo);
	}
	
	public void updateNoticeInfo(NoticeInfo noticeInfo){
		noticeDao.updateNoticeInfo(noticeInfo);
	}
	
	public NoticeInfo getNoticeInfo(int deviceId){
		return noticeDao.queryNoticeInfo(deviceId);
	}
	
}
