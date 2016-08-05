package com.framework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.framework.dao.SystemDao;
import com.framework.domain.SystemInfo;

@Service
public class SystemService {

	@Autowired
	SystemDao systemDao;
	
	public SystemInfo getSysDefault(){
		return systemDao.querySysDefault();
	}
	
	public void updateSysDefault(SystemInfo sysDefault){
		systemDao.updateSysDefault(sysDefault);
	}
	
}
