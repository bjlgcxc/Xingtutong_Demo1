package com.framework.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.framework.dao.EnvironmentDao;
import com.framework.domain.Environment;

@Service
public class EnvironmentService {

	@Autowired
	EnvironmentDao environmentDao;
	
	public void addEnvironmentArray(Environment[] environmentArray){
		environmentDao.insertEnvironmentArray(environmentArray);
	}
	
	public List<Environment> getEnvironmrnt(int deviceId,long startTime,long endTime){
		return environmentDao.quertEnvironment(deviceId, startTime, endTime);
	}
	
	public List<Environment> getHumidity(int deviceId,long startTime,long endTime){
		return environmentDao.queryHumidity(deviceId,startTime,endTime);
	}
	public List<Environment> getHumidity(int deviceId,String mac,long startTime,long endTime){
		return environmentDao.queryHumidity(deviceId,mac,startTime,endTime);
	}
	
	public List<Environment> getTemperature(int deviceId,long startTime,long endTime){
		return environmentDao.queryTemperature(deviceId,startTime,endTime);
	}
	public List<Environment> getTemperature(int deviceId,String mac,long startTime,long endTime){
		return environmentDao.queryTemperature(deviceId,mac,startTime,endTime);
	}
	
	public List<Environment> getPress(int deviceId,long startTime,long endTime){
		return environmentDao.queryPress(deviceId,startTime,endTime);
	}
	public List<Environment> getPress(int deviceId,String mac,long startTime,long endTime){
		return environmentDao.queryPress(deviceId,mac,startTime,endTime);
	}
	
}
