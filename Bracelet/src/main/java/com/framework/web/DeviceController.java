package com.framework.web;

import java.io.UnsupportedEncodingException;
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
import com.framework.domain.DeviceInfo;
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
		log.info("get device info");
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
		
		log.info("update device alias");
	}
	
}
