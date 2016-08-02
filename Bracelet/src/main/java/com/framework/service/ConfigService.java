package com.framework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.framework.dao.ConfigDao;
import com.framework.domain.ConfigInfo;

@Service
public class ConfigService {

	@Autowired
	ConfigDao configDao;
	
	public boolean hasMatchConfig(int deviceId){
		return configDao.queryConfigMatchCount(deviceId)>=1;
	}
	
	public void insertConfigInfo(ConfigInfo configInfo){
		configDao.insertConfigInfo(configInfo);
	}
	
	public void updateConfigInfo(ConfigInfo configInfo){
		configDao.updateConfigInfo(configInfo);
	}
	
	public ConfigInfo getConfigInfo(int deviceId){
		return configDao.queryConfigInfo(deviceId);
	}
	
}
