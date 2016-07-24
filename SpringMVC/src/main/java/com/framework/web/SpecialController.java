package com.framework.web;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.framework.domain.BasicInfo;
import com.framework.domain.BraceletInfo;
import com.framework.domain.Environment;
import com.framework.domain.HealthInfo;
import com.framework.domain.HeartRate;
import com.framework.domain.NoticeInfo;
import com.framework.domain.SleepInfo;
import com.framework.domain.SportInfo;
import com.framework.service.BasicInfoService;
import com.framework.service.BraceletService;
import com.framework.service.DeviceService;
import com.framework.service.EnvironmentService;
import com.framework.service.HealthService;
import com.framework.service.HeartRateService;
import com.framework.service.NoticeService;
import com.framework.service.SleepService;
import com.framework.service.SportService;

@Controller
public class SpecialController {
	
	private static final Log log =  LogFactory.getLog(SpecialController.class);
	
	@Autowired
	DeviceService deviceService;
	@Autowired
	BraceletService braceletService;
	@Autowired
	BasicInfoService basicInfoService;
	@Autowired
	NoticeService noticeService;
	@Autowired
	SportService sportService;
	@Autowired
	SleepService sleepService;
	@Autowired
	EnvironmentService environmentService;
	@Autowired
	HeartRateService heartRateService;
	@Autowired
	HealthService healthService;
	
	
	/*
	 * 保存手环状态信息、健康信息
	 */
	@RequestMapping(value="app/special/{deviceId}/saveJsonArray",method = RequestMethod.POST)
	public String specialJson(@RequestBody JSONObject[] jsonArray,@PathVariable int deviceId){
		
		for(JSONObject jsonObj:jsonArray){
			String mac = deviceService.getDeviceMac(deviceId);
		
			//手环状态信息更新	
			BraceletInfo braceletInfo = new BraceletInfo();
			braceletInfo.setMac(mac);
			braceletInfo.setBatteryLevel(jsonObj.getInt("batteryLevel"));
			braceletInfo.setBatteryState(jsonObj.getInt("batteryState"));
			braceletService.updateBraceletStateInfo(braceletInfo);
			log.info("get braceStateInfo");
		
			//插入基本信息 
			BasicInfo basicInfo = new BasicInfo();
			basicInfo.setDeviceId(deviceId);
			basicInfo.setHeight(jsonObj.getInt("height"));
			basicInfo.setWeight(jsonObj.getInt("weight"));
			basicInfo.setSex(jsonObj.getInt("sex"));
			basicInfo.setStepLength(jsonObj.getInt("stepLength"));
			basicInfo.setUnit(jsonObj.getInt("unit"));
			basicInfoService.addBasicInfo(basicInfo);
			log.info("get basicInfo");
			
			//插入提醒阈值信息 noticeThreshold
			JSONObject noticeThreshold = jsonObj.getJSONObject("noticeThreshold");
			NoticeInfo noticeInfo = (NoticeInfo)JSONObject.toBean(noticeThreshold,NoticeInfo.class);
			noticeInfo.setDeviceId(deviceId);
			noticeService.addNoticeInfo(noticeInfo);
			log.info("get noticeThreshold");
			
			//插入运动数据 sportData
			JSONArray sportData = jsonObj.getJSONArray("sportData");
			SportInfo[] sportInfoArray = (SportInfo[]) JSONArray.toArray(sportData, SportInfo.class);
			for(SportInfo sportInfo:sportInfoArray){
				sportInfo.setDeviceId(deviceId);
			}
			sportService.addSportInfoArray(sportInfoArray);
			log.info("get sportData");
			
			//插入睡眠数据 sleepData
			JSONArray sleepData = jsonObj.getJSONArray("sleepData");
			SleepInfo[] sleepInfoArray = (SleepInfo[]) JSONArray.toArray(sleepData, SleepInfo.class);
			for(SleepInfo sleepInfo:sleepInfoArray){
				sleepInfo.setDeviceId(deviceId);
			}
			sleepService.addSleepInfoArray(sleepInfoArray);
			log.info("get sleepData");
			
			//插入环境数据 environmentData
			JSONArray environmentData = jsonObj.getJSONArray("environmentData");
			Environment[] environmentArray = (Environment[])JSONArray.toArray(environmentData, Environment.class);
			for(Environment environment:environmentArray){
				environment.setDeviceId(deviceId);
			}
			environmentService.addEnvironmentArray(environmentArray);
			log.info("get environmentData");
			
			//插入心率数据 heartrateData
			JSONArray heartRateData = jsonObj.getJSONArray("heartRateData");
			HeartRate[] heartRateArray = (HeartRate[])JSONArray.toArray(heartRateData,HeartRate.class);
			for(HeartRate heartRate:heartRateArray){
				heartRate.setDeviceId(deviceId);
			}
			heartRateService.addHeartrateArray(heartRateArray);
			log.info("get heartRateData");
			
			//插入健康数据 healthData
			JSONObject healthData = jsonObj.getJSONObject("healthData");
			HealthInfo healthInfo = (HealthInfo)JSONObject.toBean(healthData,HealthInfo.class);
			healthInfo.setDeviceId(deviceId);
			healthService.addHealthInfo(healthInfo);
			log.info("get healthData");
		}
		
		return "redirect:/app/instruction/" + deviceId + "/returnJsonArray";
		
	}
	
}
