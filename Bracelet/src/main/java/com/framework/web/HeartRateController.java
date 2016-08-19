package com.framework.web;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.domain.HeartRate;
import com.framework.service.HeartRateService;

@Controller
public class HeartRateController {
	
	private static final Log log =  LogFactory.getLog(HeartRateController.class);

	@Autowired
	HeartRateService heartRateService;
	
	/*
	 * 获取心率(josn)
	 */
	@ResponseBody
	@RequestMapping(value="/heartRate/{deviceId}/getHeartRate",method = RequestMethod.GET)
	public JSONArray getHeartRate(HttpServletRequest request,@PathVariable int deviceId) throws ParseException{
		
		long startTime,endTime;
		SimpleDateFormat formater;
		int duration = Integer.parseInt(request.getParameter("duration"));
		
		//查询单日
		if(duration==1)
			formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//查询多日
		else
			formater = new SimpleDateFormat("yyyy-MM-dd");
		startTime = formater.parse(request.getParameter("startTime")).getTime();
		endTime = formater.parse(request.getParameter("endTime")).getTime();
		
		List<HeartRate> heartRateList = heartRateService.getHeartRate(deviceId, startTime, endTime);
		formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONArray jsonArray = new JSONArray();
		for(HeartRate heartRate:heartRateList){
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("heartRate", heartRate.getSize());
			jsonObj.put("time", formater.format(new Date(heartRate.getTestTime())));
			jsonArray.add(jsonObj);
		}
		
		log.info("get heartRate data");
		return jsonArray; 
	}
	
	
	/*
	 * 获取手表皮温度(josn)
	 */
	@ResponseBody
	@RequestMapping(value="/heartRate/{deviceId}/getSurfaceTem",method = RequestMethod.GET)
	public JSONArray getSurfaceTem(HttpServletRequest request,@PathVariable int deviceId) throws ParseException{
		
		long startTime,endTime;
		SimpleDateFormat formater;
		int duration = Integer.parseInt(request.getParameter("duration"));
		
		//查询单日
		if(duration==1)
			formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//查询多日
		else
			formater = new SimpleDateFormat("yyyy-MM-dd");
		startTime = formater.parse(request.getParameter("startTime")).getTime();
		endTime = formater.parse(request.getParameter("endTime")).getTime();
	
		List<HeartRate> heartRateList = heartRateService.getSurfaceTem(deviceId, startTime, endTime);
		formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONArray jsonArray = new JSONArray();
		for(HeartRate heartRate:heartRateList){
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("surfaceTem", heartRate.getSurfaceTem());
			jsonObj.put("time", formater.format(new Date(heartRate.getTestTime())));
			
			jsonArray.add(jsonObj);
		}
	
		log.info("get surface temperature");
		return jsonArray; 
	}
	
}
