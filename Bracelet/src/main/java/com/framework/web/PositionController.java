package com.framework.web;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
import com.framework.domain.DeviceInfo;
import com.framework.domain.PositionInfo;
import com.framework.service.DeviceService;
import com.framework.service.PositionService;
import com.framework.utils.Gps;
import com.framework.utils.PositionUtils;

@Controller  
public class PositionController {
	
	private static final Log log =  LogFactory.getLog(PositionController.class);
	
	@Autowired
	PositionService positionService;
	@Autowired
	DeviceService deviceService;
	
	/*
	 * 保存位置信息(json)
	 */
	@RequestMapping(value="/app/position/{deviceId}/saveJson",method = RequestMethod.POST)
	public String savePositionInfo(@RequestBody JSONObject jsonObj,@PathVariable int deviceId){
		
		PositionInfo positionInfo = (PositionInfo)JSONObject.toBean(jsonObj,PositionInfo.class);
		positionInfo.setDeviceId(deviceId);
		positionService.savePositionInfo(positionInfo);		
		
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setId(deviceId);
		deviceInfo.setConnectTime(new Timestamp(System.currentTimeMillis()));
		deviceService.updateConnectTime(deviceInfo);
		
		log.info("get position info");
		return "redirect:/app/instruction/" + deviceId + "/returnJsonArray";
	}
	
	
	/*
	 * 保存位置信息(json array)
	 */
	@RequestMapping(value="/app/position/{deviceId}/saveJsonArray",method = RequestMethod.POST)
	public String savePositionInfoList(@RequestBody JSONArray jsonArray,@PathVariable int deviceId){
		
		//保存位置信息
		PositionInfo positionInfoArray[] = (PositionInfo[])JSONArray.toArray(jsonArray, PositionInfo.class);
		for(PositionInfo positionInfo:positionInfoArray){
			positionInfo.setDeviceId(deviceId);
		}
		positionService.savePositionInfoList(positionInfoArray);
			
		//更新设备通信时间
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setId(deviceId);
		deviceInfo.setConnectTime(new Timestamp(System.currentTimeMillis()));
		deviceService.updateConnectTime(deviceInfo);	
		
		log.info("get position info,size=" + positionInfoArray.length);
		return  "redirect:/app/instruction/" + deviceId + "/returnJsonArray";
	}
	
	
	/*
	 * 获取位置信息(json Array)
	 */
	@ResponseBody
	@RequestMapping(value="/position/{deviceId}/getPositionInfo",method = RequestMethod.GET)
	public List<PositionInfo> getPositionInfoList(HttpServletRequest request,@PathVariable int deviceId) throws ParseException{
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startTime = format.parse(request.getParameter("st"));
		Date endTime = format.parse(request.getParameter("et"));
		List<PositionInfo> positionInfoList  = positionService.getPositionInfoList(deviceId, startTime.getTime(), endTime.getTime());
		
		for(PositionInfo positionInfo:positionInfoList){
			double longitude = positionInfo.getLongitude();
			double latitude = positionInfo.getLatitude();
			Gps gps = PositionUtils.gcj02_to_Wgs84_exact(latitude,longitude);
			positionInfo.setLongitude(gps.getLongitude());
			positionInfo.setLatitude(gps.getLatitude());
		}	
		
		return positionInfoList;
	}
	
}
