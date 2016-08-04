package com.framework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.framework.dao.SysDefaultDao;
import com.framework.domain.SysDefault;

@Service
public class SysDefaultService {

	@Autowired
	SysDefaultDao sysDefaultDao;
	
	public SysDefault getSysDefault(){
		return sysDefaultDao.querySysDefault();
	}
	
	public void updateSysDefault(SysDefault sysDefault){
		sysDefaultDao.updateSysDefault(sysDefault);
	}
	
}
