package com.framework.web;

import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
	 * 保存设置(json)
	 */
	@ResponseBody
	@RequestMapping(value="/app/config/{deviceId}/saveJson")
	public void saveJson(@RequestBody JSONObject jsonObj,@PathVariable int deviceId){
		ConfigInfo configInfo = (ConfigInfo)JSONObject.toBean(jsonObj,ConfigInfo.class);
		configInfo.setDeviceId(deviceId);
		
		//如果不存在，则插入
		if(!configService.hasMatchConfig(deviceId)){
			configService.insertConfigInfo(configInfo);
		}
		//存在，则更新
		else{
			configService.updateConfigInfo(configInfo);
		}
		
		log.info("save config info");
	}

	
	/*
	 * 获取设置信息
	 */
	@ResponseBody
	@RequestMapping(value="/config/{deviceId}/getConfigInfo",method = RequestMethod.GET)
	public JSONObject getConfigInfo(@PathVariable int deviceId){
		if(deviceService.hasMatchDevice(deviceId)){
			ConfigInfo configInfo = configService.getConfigInfo(deviceId);
			configInfo.braceletInterval /= 60;
			configInfo.locationInterval /= 60;
			return JSONObject.fromObject(configInfo);
		}
		
		return null;
	}
	
}
