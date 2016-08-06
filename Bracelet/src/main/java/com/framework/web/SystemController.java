package com.framework.web;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.domain.SystemInfo;
import com.framework.service.SystemService;

@Controller
public class SystemController {

	@Autowired
	SystemService systemService;
	
	
	@ResponseBody
	@RequestMapping(value="/sysDefault/getSysDefault",method=RequestMethod.GET)
	public JSONObject getSysDefault(HttpServletRequest request){
		SystemInfo systemInfo = systemService.getSysDefault();
		systemInfo.setBraceletInterval(systemInfo.getBraceletInterval()/60);
		systemInfo.setLocationInterval(systemInfo.getLocationInterval()/60);
		return JSONObject.fromObject(systemInfo);
	}
	
	@ResponseBody
	@RequestMapping(value="/sysDefault/updateSysDefault",method=RequestMethod.POST)
	public void updateSysDefault(HttpServletRequest request,SystemInfo sysInfo){
		sysInfo.setBraceletInterval(sysInfo.getBraceletInterval()*60);
		sysInfo.setLocationInterval(sysInfo.getLocationInterval()*60);
		systemService.updateSysDefault(sysInfo);
	}
	
}
