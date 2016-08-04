package com.framework.web;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.framework.service.SysDefaultService;

@Controller
public class SysDefaultController {

	@Autowired
	SysDefaultService sysDefaultService;
	
	
	@ResponseBody
	@RequestMapping(value="/sysDefault/getSysDefault")
	public JSONObject getSysDefault(){
		return JSONObject.fromObject(sysDefaultService.getSysDefault());
	} 
	
}
