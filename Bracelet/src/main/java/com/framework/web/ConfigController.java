package com.framework.web;

import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.framework.domain.ConfigInfo;
import com.framework.service.ConfigService;
import com.framework.service.DeviceService;

@Controller
public class ConfigController {
	
	private static final Log log =  LogFactory.getLog(ConfigController.class);
	
	@Autowired
	ConfigService configService;
	@Autowired
	DeviceService deviceService;
	
	/*
	 * 获取设置信息
	 */
	@ResponseBody
	@RequestMapping(value="/config/{deviceId}/getConfigInfo",method = RequestMethod.GET)
	public JSONObject getConfigInfo(@PathVariable int deviceId){
		if(deviceService.hasMatchDevice(deviceId)){
			ConfigInfo configInfo = configService.getConfigInfo(deviceId);
			configInfo.setBraceletInterval(configInfo.getBraceletInterval()/60);
			configInfo.setLocationInterval(configInfo.getLocationInterval()/60);
			return JSONObject.fromObject(configInfo);
		}
		
		log.info("get config info");
		return null;
	}
	
}
