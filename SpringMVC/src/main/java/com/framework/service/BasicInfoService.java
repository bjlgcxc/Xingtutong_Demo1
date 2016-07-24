package com.framework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.framework.dao.BasicInfoDao;
import com.framework.domain.BasicInfo;

@Service
public class BasicInfoService {

	@Autowired
	BasicInfoDao basicInfoDao;
	
	public void addBasicInfo(BasicInfo basicInfo){
		basicInfoDao.insertBasicInfo(basicInfo);
	}
	
	public BasicInfo getBasicInfo(int deviceId){
		return basicInfoDao.queryBasicInfo(deviceId);
	}
	
}
