package com.framework.web;

import java.sql.Timestamp;
import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.domain.BasicInfo;
import com.framework.domain.BraceletInfo;
import com.framework.domain.ConfigInfo;
import com.framework.domain.DeviceInfo;
import com.framework.domain.Environment;
import com.framework.domain.HealthInfo;
import com.framework.domain.HeartRate;
import com.framework.domain.Instruction;
import com.framework.domain.NoticeInfo;
import com.framework.domain.PositionInfo;
import com.framework.domain.SleepInfo;
import com.framework.domain.SportInfo;
import com.framework.domain.SystemInfo;
import com.framework.service.BasicInfoService;
import com.framework.service.BraceletService;
import com.framework.service.ConfigService;
import com.framework.service.DeviceService;
import com.framework.service.EnvironmentService;
import com.framework.service.HealthService;
import com.framework.service.HeartRateService;
import com.framework.service.InstructionService;
import com.framework.service.NoticeService;
import com.framework.service.PositionService;
import com.framework.service.SleepService;
import com.framework.service.SportService;
import com.framework.service.SystemService;

@Controller
public class RestfulController {
	
	private static final Log log =  LogFactory.getLog(RestfulController.class);
	
	@Autowired
	DeviceService deviceService;
	@Autowired
	BraceletService braceletService;
	@Autowired
	InstructionService instructionService;
	@Autowired
	ConfigService configService;
	@Autowired
	SystemService systemService;
	@Autowired
	PositionService positionService;
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
	 * �ֻ���¼app,�����ֻ���Ϣ(imei)����̨,��̨����app��Ӧ���豸id
	 */
	@ResponseBody
	@RequestMapping(value="/app/device/{imei}/appLogin")
	public synchronized JSONObject appLogin(@PathVariable String imei){	
		
		log.info("receive device_imei,"+imei);
		DeviceInfo deviceInfo = new DeviceInfo();		
		//���豸δע�ᣬ���ע�᣻����ע�ᣬ��������ʱ��
		boolean isDeviceExist = deviceService.hasMatchDevice(imei);
		if(!isDeviceExist){
			deviceInfo.setImei(imei);
			deviceInfo.setConnectTime(new Timestamp(System.currentTimeMillis()));
			deviceService.addDeviceInfo(deviceInfo);
		}
		else{
			deviceInfo.setId(deviceService.getDeviceId(imei));
			deviceInfo.setConnectTime(new Timestamp(System.currentTimeMillis()));
			deviceService.updateDeviceInfo(deviceInfo);
		}		
		
		//configInfo
		int id = deviceService.getDeviceId(imei);
		ConfigInfo configInfo = null;
		if(!configService.hasMatchConfig(id)){
			//���豸û��������Ϣ,�����Ĭ������
			SystemInfo sysDefault = systemService.getSysDefault();
			configInfo = new ConfigInfo();
			configInfo.setDeviceId(id);
			configInfo.setBraceletInterval(sysDefault.getBraceletInterval());
			configInfo.setBraceletUpload(sysDefault.getBraceletUpload());
			configInfo.setLocationInterval(sysDefault.getLocationInterval());
			configInfo.setLocationUpload(sysDefault.getLocationUpload());
			configInfo.setLocateInterval(sysDefault.getLocateInterval());
			configInfo.setLocateTimes(sysDefault.getLocateTimes());
			configInfo.setTeleNumber(sysDefault.getTeleNumber());
			configService.insertConfigInfo(configInfo);
			log.info("insert device config info");
		}
		else{
			configInfo = configService.getConfigInfo(id);
		}
		
		log.info("send device_id and config_info to device");
		JSONObject jsonObj = JSONObject.fromObject(configInfo);
		jsonObj.put("id",configInfo.getDeviceId());
		return jsonObj;
	}
	
	
	/*
	 * �ֻ���������,app�����ֻ���Ϣ����̨
	 */
	@RequestMapping(value="/app/device/{deviceId}/bluetoothConn",method = RequestMethod.POST)
	public String bluetoothConn(@RequestBody BraceletInfo braceletInfo,@PathVariable int deviceId){
		
		//�����豸��mac��ַ
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setId(deviceId);
		deviceInfo.setMac(braceletInfo.getMac());
		deviceInfo.setConnectTime(new Timestamp(System.currentTimeMillis()));
		deviceService.updateDeviceInfo(deviceInfo);
		
		if(braceletInfo.getMac()==null){
			log.info("the mac received is null!");
		}
		else{
			//�����ֻ�����Ϣ
			boolean isBraceletExist = braceletService.hasMatchBracelet(braceletInfo.getMac());
			if(!isBraceletExist)
				braceletService.addBraceletInfo(braceletInfo);
			else{
				braceletService.updateBraceletInfo(braceletInfo);
			}
			log.info("receive bracelet_mac from device,mac=" + braceletInfo.getMac());
		}
		
		return "redirect:/app/instruction/" + deviceId + "/returnJsonArray";
	}
	
	
	/*
	 * ������豸���յ�������Ϣ(json)
	 */
	@ResponseBody
	@RequestMapping(value="/app/config/{deviceId}/saveJson")
	public void saveJson(@RequestBody JSONObject jsonObj,@PathVariable int deviceId){
		
		ConfigInfo configInfo = (ConfigInfo)JSONObject.toBean(jsonObj,ConfigInfo.class);
		configInfo.setDeviceId(deviceId);
		
		//����������豸��������Ϣ�������
		boolean isConfigExit = configService.hasMatchConfig(deviceId);
		if(!isConfigExit){
			configService.insertConfigInfo(configInfo);
		}
		//������ڣ������
		else{
			configService.updateConfigInfo(configInfo);
		}
		
		log.info("receive config_info from device)");
	}
	
	
	/*
	 * ������豸���յ�λ����Ϣ(json array)
	 */
	@RequestMapping(value="/app/position/{deviceId}/saveJsonArray",method = RequestMethod.POST)
	public String savePositionInfoList(@RequestBody JSONArray jsonArray,@PathVariable int deviceId){
		
		//����λ����Ϣ
		PositionInfo positionInfoArray[] = (PositionInfo[])JSONArray.toArray(jsonArray, PositionInfo.class);
		for(PositionInfo positionInfo:positionInfoArray){
			positionInfo.setDeviceId(deviceId);
		}
		positionService.savePositionInfoList(positionInfoArray);
			
		//�����豸ͨ��ʱ��
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setId(deviceId);
		deviceInfo.setConnectTime(new Timestamp(System.currentTimeMillis()));
		deviceService.updateDeviceInfo(deviceInfo);	
		
		log.info("receive position info from device,size=" + positionInfoArray.length);
		return  "redirect:/app/instruction/" + deviceId + "/returnJsonArray";
	}
	
	
	/*
	 * ������豸���յ��ֻ�״̬��Ϣ��������Ϣ��
	 */
	@RequestMapping(value="app/special/{deviceId}/saveJsonArray",method = RequestMethod.POST)
	public String getSpecial(@RequestBody JSONObject[] jsonArray,@PathVariable int deviceId){
		String mac = null;
		for(JSONObject jsonObj:jsonArray){
			//��ȡmac
			mac = jsonObj.getString("mac");
			if(mac==null)
				mac = deviceService.getDeviceMac(deviceId);
			//�ֻ�״̬��Ϣ����	
			BraceletInfo braceletInfo = new BraceletInfo();
			braceletInfo.setMac(mac);
			braceletInfo.setBatteryLevel(jsonObj.getInt("batteryLevel"));
			braceletInfo.setBatteryState(jsonObj.getInt("batteryState"));
			braceletService.updateBraceletStateInfo(braceletInfo);
			log.info("receive bracelet_state_info from device");
		
			//���������Ϣ 
			BasicInfo basicInfo = new BasicInfo();
			basicInfo.setDeviceId(deviceId);
			basicInfo.setMac(mac);
			basicInfo.setHeight(jsonObj.getInt("height"));
			basicInfo.setWeight(jsonObj.getInt("weight"));
			basicInfo.setSex(jsonObj.getInt("sex"));
			basicInfo.setStepLength(jsonObj.getInt("stepLength"));
			basicInfo.setUnit(jsonObj.getInt("unit"));
			if(basicInfoService.hasMatchBasicInfo(deviceId)){
				basicInfoService.updateBasicInfo(basicInfo);
			}
			else{
				basicInfoService.addBasicInfo(basicInfo);
			}
			log.info("receive basic_info from device");
			
			//����������ֵ��Ϣ noticeThreshold
			JSONObject noticeThreshold = jsonObj.getJSONObject("noticeThreshold");
			NoticeInfo noticeInfo = (NoticeInfo)JSONObject.toBean(noticeThreshold,NoticeInfo.class);
			noticeInfo.setDeviceId(deviceId);
			noticeInfo.setMac(mac);
			if(noticeService.hasMatchNoticeInfo(deviceId)){
				noticeService.updateNoticeInfo(noticeInfo);
			}
			else{
				noticeService.addNoticeInfo(noticeInfo);
			}
			log.info("receive notice_info from device");
			
			//�����˶����� sportData
			JSONArray sportData = jsonObj.getJSONArray("sportData");
			SportInfo[] sportInfoArray = (SportInfo[]) JSONArray.toArray(sportData, SportInfo.class);
			for(SportInfo sportInfo:sportInfoArray){
				sportInfo.setDeviceId(deviceId);
			}
			sportService.addSportInfoArray(sportInfoArray);
			log.info("receive sport_data from device");
			
			//����˯������ sleepData
			JSONArray sleepData = jsonObj.getJSONArray("sleepData");
			SleepInfo[] sleepInfoArray = (SleepInfo[]) JSONArray.toArray(sleepData, SleepInfo.class);
			for(SleepInfo sleepInfo:sleepInfoArray){
				sleepInfo.setDeviceId(deviceId);
			}
			sleepService.addSleepInfoArray(sleepInfoArray);
			log.info("receive sleep_data from device");
			
			//���뻷������ environmentData
			JSONArray environmentData = jsonObj.getJSONArray("environmentData");
			Environment[] environmentArray = (Environment[])JSONArray.toArray(environmentData, Environment.class);
			for(Environment environment:environmentArray){
				environment.setDeviceId(deviceId);
			}
			environmentService.addEnvironmentArray(environmentArray);
			log.info("receive environment_data from device");
			
			//������������ heartrateData
			JSONArray heartRateData = jsonObj.getJSONArray("heartRateData");
			HeartRate[] heartRateArray = (HeartRate[])JSONArray.toArray(heartRateData,HeartRate.class);
			for(HeartRate heartRate:heartRateArray){
				heartRate.setDeviceId(deviceId);
			}
			heartRateService.addHeartrateArray(heartRateArray);
			log.info("receive heartRate_data from device");
			
			//���뽡������ healthData
			JSONObject healthData = jsonObj.getJSONObject("healthData");
			HealthInfo healthInfo = (HealthInfo)JSONObject.toBean(healthData,HealthInfo.class);
			healthInfo.setDeviceId(deviceId);
			healthService.addHealthInfo(healthInfo);
			log.info("get real-time_health_data from device");
		}
		
		return "redirect:/app/instruction/" + deviceId + "/returnJsonArray";
	}
	

	/*
	 * ����ָ��豸(josnArray)
	 */
	@ResponseBody
	@RequestMapping(value="/app/instruction/{deviceId}/returnJsonArray")
	public List<Instruction> returnInstructions(@PathVariable int deviceId){
		 List<Instruction> instructions = instructionService.getInstructions(deviceId);
		 for(Instruction instruction:instructions){
			 instructionService.updateInstructionSendInfo(instruction);
		 } 
		 
		 log.info("send instructions to device,size=" + instructions.size());
		 return instructions;
	}

}
