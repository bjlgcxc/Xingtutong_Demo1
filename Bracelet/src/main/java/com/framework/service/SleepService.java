package com.framework.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.framework.dao.SleepDao;
import com.framework.domain.SleepInfo;

@Service
public class SleepService {

	@Autowired
	SleepDao sleepDao;
	
	public void addSleepInfoArray(SleepInfo[] sleepInfoArray){
		sleepDao.insertSleepInfoArray(sleepInfoArray);
	}
	
	public List<SleepInfo> getSleepInfo(int deviceId,long startTime,long endTime){
		return sleepDao.getSleepInfo(deviceId, startTime, endTime);
	}
	
}
