package com.framework.web;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.framework.domain.BraceletInfo;
import com.framework.domain.ConfigInfo;
import com.framework.domain.DeviceInfo;
import com.framework.domain.SystemInfo;
import com.framework.service.BraceletService;
import com.framework.service.ConfigService;
import com.framework.service.DeviceService;
import com.framework.service.InstructionService;
import com.framework.service.SystemService;

@Controller
public class DeviceController {
	
	private static final Log log =  LogFactory.getLog(DeviceController.class);
	
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
	
	
	/*
	 * 手机登录app,发送手机信息(imei)到后台,后台返给app相应的设备id
	 */
	@ResponseBody
	@RequestMapping(value="/app/device/{imei}/appLogin")
	public DeviceInfo appLogin(@PathVariable String imei){	
		
		log.info("get phone imei");
		DeviceInfo deviceInfo = new DeviceInfo();
		
		//若设备未注册，添加注册；若已注册，更新连接时间
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
		
		int id = deviceService.getDeviceId(imei);
		//若设备没有配置信息,则插入默认配置
		if(!configService.hasMatchConfig(id)){
			SystemInfo sysDefault = systemService.getSysDefault();
			ConfigInfo configInfo = new ConfigInfo();
			configInfo.setDeviceId(id);
			configInfo.setBraceletInterval(sysDefault.getBraceletInterval());
			configInfo.setBraceletUpload(sysDefault.getBraceletUpload());
			configInfo.setLocationInterval(sysDefault.getLocationInterval());
			configInfo.setLocationUpload(sysDefault.getLocationUpload());
			configInfo.setLocateInterval(sysDefault.getLocateInterval());
			configInfo.setLocateTimes(sysDefault.getLocateTimes());
			configInfo.setTeleNumber(sysDefault.getTeleNumber());
			configService.insertConfigInfo(configInfo);
		}
		log.info("insert device config info");
		
		//返回imei和id的对应信息
		deviceInfo = new DeviceInfo();
		deviceInfo.setId(id);
		deviceInfo.setImei(imei);
		log.info("return device id");
		return deviceInfo;
	}
	
	
	/*
	 * 手机连接蓝牙,app发送手环信息到后台
	 */
	@RequestMapping(value="/app/device/{deviceId}/bluetoothConn",method = RequestMethod.POST)
	public String bluetoothConn(BraceletInfo braceletInfo,@PathVariable int deviceId){
		
		//更新设备的mac地址
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setId(deviceId);
		deviceInfo.setMac(braceletInfo.getMac());
		deviceInfo.setConnectTime(new Timestamp(System.currentTimeMillis()));
		deviceService.updateDeviceInfo(deviceInfo);
		
		//更新手环的信息
		boolean isBraceletExist = braceletService.hasMatchBracelet(braceletInfo.getMac());
		if(!isBraceletExist)
			braceletService.addBraceletInfo(braceletInfo);
		else{
			braceletService.updateBraceletInfo(braceletInfo);
		}
		log.info("get bracelet mac and other info");
		
		return "redirect:/app/instruction/" + deviceId + "/returnJsonArray";
	}
	
	
	/*
	 * 获取设备信息列表
	 */
	@RequestMapping(value="device.html")
	public String getDeviceInfo(HttpServletRequest request) throws UnsupportedEncodingException{
	
		String deviceId = request.getParameter("deviceId");
		String deviceName = (String) request.getParameter("deviceName");
		String deviceAlias = request.getParameter("deviceAlias");
		
		JSONArray jsonArray = new JSONArray();
		if(deviceId!="" || deviceId!=null || deviceAlias!="" || deviceAlias!=null){
			List<DeviceInfo> deviceInfoList = deviceService.getDeviceInfo(deviceId,null,deviceAlias);
			if(deviceInfoList!=null){
				for(DeviceInfo deviceInfo:deviceInfoList){
					List<BraceletInfo> braceletInfoList = braceletService.getBraceletInfo(deviceInfo.getMac(),deviceName); 
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("id", deviceInfo.getId());
					jsonObj.put("alias",deviceInfo.getAlias());
					jsonObj.put("connectTime",deviceInfo.getConnectTime().getTime());
					if(braceletInfoList!=null){			
						jsonObj.put("name",braceletInfoList.get(0).getName());					
						jsonArray.add(jsonObj);
					}
				}
			}
		}
		else{
			List<BraceletInfo> braceletInfoList = braceletService.getBraceletInfo(null,deviceName); 
			if(braceletInfoList!=null){
				for(BraceletInfo braceletInfo:braceletInfoList){
					JSONObject jsonObj = new JSONObject();
					String mac = braceletInfo.getMac();
					List<DeviceInfo> deviceInfoList = deviceService.getDeviceInfo(null,mac,null);
					if(deviceInfoList!=null){
						jsonObj.put("id",deviceInfoList.get(0).getId());
						jsonObj.put("name",braceletInfo.getName());
						jsonObj.put("alias", deviceInfoList.get(0).getAlias());
						jsonObj.put("connectTime",deviceInfoList.get(0).getConnectTime().getTime());
						jsonArray.add(jsonObj);
				    }
				}
			}
		}
		
		request.setAttribute("deviceInfo", jsonArray);
		return "device";
	}
	
	
	/*
	 * 修改设备别名
	 */
	@ResponseBody
	@RequestMapping(value="/device/{deviceId}/updateDeviceAlias",method = RequestMethod.POST)
	public void updateBraceletAlias(HttpServletRequest request,@PathVariable int deviceId){
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setId(deviceId);
		deviceInfo.setAlias(request.getParameter("deviceAlias"));
		deviceService.updateDeviceInfo(deviceInfo);
	}
	
}
