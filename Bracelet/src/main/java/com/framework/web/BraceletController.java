package com.framework.web;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.framework.domain.BraceletInfo;
import com.framework.service.BraceletService;
import com.framework.service.DeviceService;

@Controller
public class BraceletController {
	
	@Autowired
	BraceletService braceletService;
	@Autowired
	DeviceService deviceService;
	
	/*
	 * 保存手环基本信息
	 */
	@ResponseBody
	@RequestMapping(value="/bracelet/{deviceId}/getBraceletInfo",method = RequestMethod.GET)
	public BraceletInfo getBraceletInfo(HttpServletRequest request,@PathVariable int deviceId){
		String mac = deviceService.getDeviceMac(deviceId);
		if(mac==null)
			return null;
		else
			return braceletService.getBraceletInfo(mac);		
	}
	
}
