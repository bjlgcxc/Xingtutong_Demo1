package com.framework.web;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.domain.SystemInfo;
import com.framework.service.SystemService;

@Controller
public class SystemController {

	@Autowired
	SystemService systemService;
	
	
	@ResponseBody
	@RequestMapping(value="/sysDefault/getSysDefault")
	public JSONObject getSysDefault(){
		return JSONObject.fromObject(systemService.getSysDefault());
	}
	
	@ResponseBody
	@RequestMapping(value="/sysDefault/updateSysDefault")
	public void updateSysDefault(SystemInfo sysDefault){
		systemService.updateSysDefault(sysDefault);
	}
	
}
