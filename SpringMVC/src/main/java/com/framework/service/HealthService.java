package com.framework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.framework.dao.HealthDao;
import com.framework.domain.HealthInfo;

@Service
public class HealthService {

	@Autowired
	HealthDao healthDao;
	
	public void addHealthInfo(HealthInfo healthInfo){
		healthDao.insertHealthInfo(healthInfo);
	}
	
	public HealthInfo getHealthInfo(int deviceId){
		return healthDao.queryHealthInfo(deviceId);
	}
	
}
