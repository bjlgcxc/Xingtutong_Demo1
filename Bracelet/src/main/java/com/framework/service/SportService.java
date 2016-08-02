package com.framework.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.framework.dao.SportDao;
import com.framework.domain.SportInfo;

@Service
public class SportService {

	@Autowired
	SportDao sportDao;
	
	public void addSportInfoArray(SportInfo[] sportInfoArray){
		sportDao.insertSportInfo(sportInfoArray);
	}
	
	public List<SportInfo> getSportInfo(int deviceId,long startTime,long endTime){
		return sportDao.getSportInfoInfo(deviceId, startTime, endTime);
	}
	
}
