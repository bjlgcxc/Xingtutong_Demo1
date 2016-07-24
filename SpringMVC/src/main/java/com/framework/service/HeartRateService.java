package com.framework.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.framework.dao.HeartRateDao;
import com.framework.domain.HeartRate;

@Service
public class HeartRateService {
	
	@Autowired
	HeartRateDao heartRateDao;
	
	public void addHeartrateArray(HeartRate[] heartRateArray){
		heartRateDao.insertHeartRateArray(heartRateArray);
	}

	public List<HeartRate> getHeartRate(int deviceId,long startTime,long endTime){
		return heartRateDao.queryHeartRate(deviceId, startTime, endTime);
	}
	
	public List<HeartRate> getSurfaceTem(int deviceId,long startTime,long endTime){
		return heartRateDao.querySurfaceTem(deviceId, startTime, endTime);
	}
	
}
