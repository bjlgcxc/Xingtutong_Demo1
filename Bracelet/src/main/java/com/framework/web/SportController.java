package com.framework.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.framework.domain.SportInfo;
import com.framework.service.BasicInfoService;
import com.framework.service.DeviceService;
import com.framework.service.SportService;

@Controller
public class SportController {
	
	private static final Log log =  LogFactory.getLog(SportController.class);
	
	@Autowired
	DeviceService deviceService;
	@Autowired
	SportService sportService;
	@Autowired
	BasicInfoService basicInfoService;
	
	/*
	 * 获取卡路里(json)
	 */
	@ResponseBody
	@RequestMapping(value="/sport/{deviceId}/getCalorieInfo",method = RequestMethod.GET)
	public JSONArray getCalorieInfo(HttpServletRequest request,@PathVariable int deviceId) throws ParseException{
		
		int duration = Integer.parseInt(request.getParameter("duration"));
		JSONArray jsonArray = new JSONArray();
		
		log.info("get calorie data");
		//一天的卡路里数据
		if(duration==1){
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long startTime = formater.parse(request.getParameter("startTime")).getTime();
			long endTime = formater.parse(request.getParameter("endTime")).getTime();
			
			String mac = deviceService.getDeviceMac(deviceId);
			List<SportInfo> sportInfoList = sportService.getSportInfo(deviceId,mac,startTime, endTime);
			for(SportInfo sportInfo:sportInfoList){
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("startTime",formater.format(new Date(sportInfo.getStartTime())));
				jsonObj.put("endTime", formater.format(new Date(sportInfo.getStartTime()+sportInfo.getDuration())));
				jsonObj.put("duration",sportInfo.getDuration()/3600000%24 + "小时" + sportInfo.getDuration()/60000%60 + "分钟" + sportInfo.getDuration()/1000%60 + "秒");
				if(sportInfo.getType()==0)
					jsonObj.put("type", "步行");
				else if(sportInfo.getType()==1)
					jsonObj.put("type", "跑步");
				jsonObj.put("calorie", sportInfo.getCalorie() + " 大卡");
			
				jsonArray.add(jsonObj);
			}
			return jsonArray;
		}
		//多天的卡路里数据统计(步行消耗，跑步消耗，总消耗)
		else{
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
			long startTime = formater.parse(request.getParameter("startTime")).getTime();
			long endTime = formater.parse(request.getParameter("endTime")).getTime();
			
			String mac = deviceService.getDeviceMac(deviceId);
			List<SportInfo> sportInfoList = sportService.getSportInfo(deviceId,mac,startTime, endTime+86400000);			
			List<String> dateList = new ArrayList<String>();
			Date et = new Date(endTime);	
			Date st = new Date(startTime);
			for(Date d=st;d.getTime()<=et.getTime();d.setTime(d.getTime()+24*3600*1000)){
				dateList.add(formater.format(d));
			}	
			
			int day = (int) ((endTime-startTime) / 1000 / 60 / 60 / 24) + 1;
			int walk[] = new int[day];
			int run[] = new int[day];
			for(SportInfo sportInfo:sportInfoList){
				int index = dateList.indexOf(formater.format(sportInfo.getStartTime()));
				if(sportInfo.getType()==0){
					walk[index] += sportInfo.getCalorie();
				}
				else if(sportInfo.getType()==1){
					run[index] += sportInfo.getCalorie();
				}
			}
			for(int i=0;i<day;i++){
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("date", dateList.get(i));
				jsonObj.put("walk",walk[i] + " 大卡");
				jsonObj.put("run",run[i] + " 大卡");
				jsonObj.put("total", (walk[i]+run[i]) + " 大卡");
				jsonArray.add(jsonObj);
			}
			return jsonArray;
		}
		
	}
	
	
	/*
	 * 获取步数(json)
	 */
	@ResponseBody
	@RequestMapping(value="/sport/{deviceId}/getStepInfo",method = RequestMethod.GET)
	public JSONArray getStepInfo(HttpServletRequest request,@PathVariable int deviceId) throws ParseException{
		
		int duration = Integer.parseInt(request.getParameter("duration"));
		JSONArray jsonArray = new JSONArray();
		
		log.info("get step data");
		//一天的卡路里数据
		if(duration==1){
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long startTime = formater.parse(request.getParameter("startTime")).getTime();
			long endTime = formater.parse(request.getParameter("endTime")).getTime();
			
			String mac = deviceService.getDeviceMac(deviceId);
			List<SportInfo> sportInfoList = sportService.getSportInfo(deviceId,mac,startTime, endTime);
			for(SportInfo sportInfo:sportInfoList){
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("startTime",formater.format(new Date(sportInfo.getStartTime())));
				jsonObj.put("endTime", formater.format(new Date(sportInfo.getStartTime()+sportInfo.getDuration())));
				jsonObj.put("duration",sportInfo.getDuration()/3600000%24 + "小时" + sportInfo.getDuration()/60000%60 + "分钟" + sportInfo.getDuration()/1000%60 + "秒");
				if(sportInfo.getType()==0)
					jsonObj.put("type", "步行");
				else if(sportInfo.getType()==1)
					jsonObj.put("type", "跑步");
				jsonObj.put("countStep", sportInfo.getCountStep() + " 步");
			
				jsonArray.add(jsonObj);
			}
			return jsonArray;
		}
		//多天的步数数据统计(每天的步行步数，跑步步数，总步数)
		else{
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
			long startTime = formater.parse(request.getParameter("startTime")).getTime();
			long endTime = formater.parse(request.getParameter("endTime")).getTime();
			
			String mac = deviceService.getDeviceMac(deviceId);
			List<SportInfo> sportInfoList = sportService.getSportInfo(deviceId,mac,startTime, endTime+86400000);
			List<String> dateList = new ArrayList<String>();
			Date et = new Date(endTime);	
			Date st = new Date(startTime);
			for(Date d=st;d.getTime()<=et.getTime();d.setTime(d.getTime()+24*3600*1000)){
				dateList.add(formater.format(d));
			}	
			
			int day = (int) ((endTime-startTime) / 1000 / 60 / 60 / 24) + 1;
			int walk[] = new int[day];
			int run[] = new int[day];
			for(SportInfo sportInfo:sportInfoList){
				int index = dateList.indexOf(formater.format(sportInfo.getStartTime()));
				if(sportInfo.getType()==0){
					walk[index] += sportInfo.getCountStep();
				}
				else if(sportInfo.getType()==1){
					run[index] += sportInfo.getCountStep();
				}
			}
			for(int i=0;i<day;i++){
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("date", dateList.get(i));
				jsonObj.put("walk",walk[i] + " 步");
				jsonObj.put("run",run[i] + " 步");
				jsonObj.put("total", (walk[i]+run[i]) + " 步");
				jsonArray.add(jsonObj);
			}
			return jsonArray;
		}
		
	}

	
	/*
	 * 获取里程(json)
	 */
	@ResponseBody
	@RequestMapping(value="/sport/{deviceId}/getMileageInfo",method = RequestMethod.GET)
	public JSONArray getMileageInfo(HttpServletRequest request,@PathVariable int deviceId) throws ParseException{
		//步长
		float feetLong = basicInfoService.getBasicInfo(deviceId).getStepLength();
	    java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");  
	    
	    int duration = Integer.parseInt(request.getParameter("duration"));
		JSONArray jsonArray = new JSONArray();
		
		log.info("get mileage data");
		//一天的里程数据
		if(duration==1){
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long startTime = formater.parse(request.getParameter("startTime")).getTime();
			long endTime = formater.parse(request.getParameter("endTime")).getTime();
			
			String mac = deviceService.getDeviceMac(deviceId);
			List<SportInfo> sportInfoList = sportService.getSportInfo(deviceId,mac,startTime, endTime);
			for(SportInfo sportInfo:sportInfoList){
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("startTime",formater.format(new Date(sportInfo.getStartTime())));
				jsonObj.put("endTime", formater.format(new Date(sportInfo.getStartTime()+sportInfo.getDuration())));
				jsonObj.put("duration",sportInfo.getDuration()/3600000%24 + "小时" + sportInfo.getDuration()/60000%60 + "分钟" + sportInfo.getDuration()/1000%60 + "秒");
				if(sportInfo.getType()==0)
					jsonObj.put("type", "步行");
				else if(sportInfo.getType()==1)
					jsonObj.put("type", "跑步");
				
				jsonObj.put("mileage", df.format(sportInfo.getCountStep()*feetLong/100/1000.0) + " km");
			
				jsonArray.add(jsonObj);
			}
			return jsonArray;
		}
		//多天的里程数据统计(每天步行里程，跑步里程，总里程)
		else{	
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
			long startTime = formater.parse(request.getParameter("startTime")).getTime();
			long endTime = formater.parse(request.getParameter("endTime")).getTime();
			
			String mac = deviceService.getDeviceMac(deviceId);
			List<SportInfo> sportInfoList = sportService.getSportInfo(deviceId,mac,startTime, endTime+86400000);
			List<String> dateList = new ArrayList<String>();
			Date et = new Date(endTime);	
			Date st = new Date(startTime);
			for(Date d=st;d.getTime()<=et.getTime();d.setTime(d.getTime()+24*3600*1000)){
				dateList.add(formater.format(d));
			}	
			
			int day = (int) ((endTime-startTime) / 1000 / 60 / 60 / 24) + 1;
			int walk[] = new int[day];
			int run[] = new int[day];
			for(SportInfo sportInfo:sportInfoList){
				int index = dateList.indexOf(formater.format(sportInfo.getStartTime()));
				if(sportInfo.getType()==0){
					walk[index] += sportInfo.getCountStep();
				}
				else if(sportInfo.getType()==1){
					run[index] += sportInfo.getCountStep();
				}
			}
			for(int i=0;i<day;i++){
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("date", dateList.get(i));
				jsonObj.put("walk",df.format(walk[i]*feetLong/100/1000.0) + " km");
				jsonObj.put("run",df.format(run[i]*feetLong/100/1000.0) + " km");
				jsonObj.put("total",df.format((walk[i]+run[i])*feetLong/100/1000.0) + " km");
				jsonArray.add(jsonObj);
			}
			return jsonArray;
		}
		
	}
	
}
