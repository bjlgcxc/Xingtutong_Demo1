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
import com.framework.domain.BasicInfo;
import com.framework.domain.HealthInfo;
import com.framework.service.BasicInfoService;
import com.framework.service.DeviceService;
import com.framework.service.HealthService;

@Controller  
public class HealthController {
	
	private static final Log log =  LogFactory.getLog(HealthController.class);
	
	@Autowired
	DeviceService deviceService;
	@Autowired
	HealthService healthService;
	@Autowired
	BasicInfoService basicInfoService;
	
	/*
	 * 获取健康信息(json)
	 */
	@ResponseBody
	@RequestMapping(value="health/{deviceId}/getHealthInfo",method = RequestMethod.GET)
	public JSONObject getHealthInfo(@PathVariable int deviceId){
		
		String mac = deviceService.getDeviceMac(deviceId);
		HealthInfo healthInfo = healthService.getHealthInfo(deviceId,mac);
		BasicInfo basicInfo = basicInfoService.getBasicInfo(deviceId,mac);
		JSONObject jsonObj = new JSONObject();
		
		//数据是否存在
		if(basicInfo!=null){
			jsonObj.put("height", basicInfo.getHeight());
			jsonObj.put("weight", basicInfo.getWeight());
			jsonObj.put("step", healthInfo.getStep());
		}
		if(healthInfo!=null){
			jsonObj.put("mileage", healthInfo.getMileage());
			jsonObj.put("calorie", healthInfo.getCalorie());
			jsonObj.put("sleepTime", healthInfo.getSleepTime());
			jsonObj.put("heartRateSize", healthInfo.getHeartRateSize());
			jsonObj.put("surfaceTem", healthInfo.getSurfaceTem());
			jsonObj.put("temperature", healthInfo.getTemperature());
			jsonObj.put("humidity", healthInfo.getHumidity());
			jsonObj.put("press", healthInfo.getPress());
		}
		
		log.info("get real-time health info");
		if(basicInfo==null && healthInfo==null)
			return null;
		else
			return jsonObj;
	}
	
}
