package com.framework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.framework.dao.BasicInfoDao;
import com.framework.domain.BasicInfo;

@Service
public class BasicInfoService {

	@Autowired
	BasicInfoDao basicInfoDao;
	
	public boolean hasMatchBasicInfo(int deviceId){
		return basicInfoDao.getMatchCount(deviceId)>0;
	}
	
	public void addBasicInfo(BasicInfo basicInfo){
		basicInfoDao.insertBasicInfo(basicInfo);
	}
	
	public void updateBasicInfo(BasicInfo basicInfo){
		basicInfoDao.updateBasicInfo(basicInfo);
	}
	
	public BasicInfo getBasicInfo(int deviceId){
		return basicInfoDao.queryBasicInfo(deviceId);
	}
	public BasicInfo getBasicInfo(int deviceId,String mac){
		return basicInfoDao.queryBasicInfo(deviceId,mac);
	}
	
}
