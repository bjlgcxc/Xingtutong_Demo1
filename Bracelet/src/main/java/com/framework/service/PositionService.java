package com.framework.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.framework.dao.PositionDao;
import com.framework.domain.PositionInfo;

@Service 
public class PositionService {
	
	@Autowired
	PositionDao positionDao;
	
	public void savePositionInfo(PositionInfo obj){
		positionDao.insertPositionInfo(obj);
	}
	
	public void savePositionInfoList(PositionInfo[] positionInfoArray){
		positionDao.insertPositionInfoList(positionInfoArray);
	}
	
	public List<PositionInfo> getPositionInfoList(int deviceId,long startTime,long endTime){
		return positionDao.queryPositionInfoList(deviceId, startTime, endTime);
	}

}
