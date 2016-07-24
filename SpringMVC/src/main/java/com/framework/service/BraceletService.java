package com.framework.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.framework.dao.BraceletDao;
import com.framework.domain.BraceletInfo;

@Service
public class BraceletService {
	
	@Autowired
	BraceletDao braceletDao;
	
	//bracelet
	public boolean hasMatchBracelet(String mac){
		return braceletDao.getBraceletMatchCount(mac) > 0;
	}
	
	public void addBraceletInfo(BraceletInfo braceletInfo){
		braceletDao.insertBraceletInfo(braceletInfo);
	}
	
	public void updateBraceletInfo(BraceletInfo braceletInfo){
		braceletDao.updateBraceletInfo(braceletInfo);
	}
	
	public void updateBraceletStateInfo(BraceletInfo braceletInfo){
		braceletDao.updateBraceletStateInfo(braceletInfo);
	}
	
	public BraceletInfo getBraceletInfo(String mac){
		return braceletDao.queryBraceletInfo(mac);
	}
	
	public List<BraceletInfo> getBraceletInfo(String mac,String deviceName,String deviceAlias){
		return braceletDao.queryBraceletInfo(mac,deviceName,deviceAlias);
	} 

}
