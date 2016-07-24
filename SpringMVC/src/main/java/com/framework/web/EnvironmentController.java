package com.framework.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.framework.domain.Environment;
import com.framework.service.EnvironmentService;

@Controller
public class EnvironmentController {

	@Autowired
	EnvironmentService environmentService;

	
	/*
	 * 获取湿度(josn)
	 */
	@ResponseBody
	@RequestMapping(value="/environment/{deviceId}/getHumidity",method = RequestMethod.GET)
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
		
		List<Environment> environmentList = environmentService.getHumidity(deviceId,startTime,endTime);
		formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONArray jsonArray = new JSONArray();
		for(Environment environment:environmentList){
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("humidity", environment.getHumidity());
			jsonObj.put("time", formater.format(new Date(environment.getTestTime())));
				
			jsonArray.add(jsonObj);
		}
		
		return jsonArray; 
	}
	
	
	/*
	 * 获取温度(json)
	 */
	@ResponseBody
	@RequestMapping(value="/environment/{deviceId}/getTemperature",method = RequestMethod.GET)
	public JSONArray getTemperature(HttpServletRequest request,@PathVariable int deviceId) throws ParseException{
				
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
		
		List<Environment> environmentList = environmentService.getTemperature(deviceId, startTime, endTime);	
		formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONArray jsonArray = new JSONArray();
		for(Environment environment:environmentList){
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("temperature", environment.getTemperature());
			jsonObj.put("time", formater.format(new Date(environment.getTestTime())));
					
			jsonArray.add(jsonObj);
		}
			
		return jsonArray; 
	}	
	
	
	/*
	 * 获取气压(json)
	 */
	@ResponseBody
	@RequestMapping(value="/environment/{deviceId}/getPress",method = RequestMethod.GET)
	public JSONArray getPress(HttpServletRequest request,@PathVariable int deviceId) throws ParseException{
					
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
		
		List<Environment> environmentList = environmentService.getPress(deviceId, startTime, endTime);
		formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONArray jsonArray = new JSONArray();
		for(Environment environment:environmentList){
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("press", environment.getPress());
			jsonObj.put("time", formater.format(new Date(environment.getTestTime())));
						
			jsonArray.add(jsonObj);
		}
				
		return jsonArray; 
	}	
	
}
