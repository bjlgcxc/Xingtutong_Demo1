package com.framework.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.framework.domain.SleepInfo;
import com.framework.service.SleepService;

@Controller
public class SleepController {
	
	@Autowired
	SleepService sleepService;
	
	
	/*
	 * ��ȡ˯����Ϣ
	 */
	@ResponseBody
	@RequestMapping(value="/sleep/{deviceId}/getSleepInfo",method = RequestMethod.GET)
	public JSONArray getSleepInfo(HttpServletRequest request,@PathVariable int deviceId) throws ParseException{
		
		int duration = Integer.parseInt(request.getParameter("duration"));
		JSONArray jsonArray = new JSONArray();
		
		//һ���˯������
		if(duration==1){
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long startTime = formater.parse(request.getParameter("startTime")).getTime();
			long endTime = formater.parse(request.getParameter("endTime")).getTime();
			
			List<SleepInfo> sleepInfoList = sleepService.getSleepInfo(deviceId, startTime, endTime);
			for(SleepInfo sleepInfo:sleepInfoList){
				if(sleepInfo.type==2||sleepInfo.type==3)
					continue;
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("startTime",formater.format(new Date(sleepInfo.startTime)));
				jsonObj.put("endTime", formater.format(new Date(sleepInfo.startTime+sleepInfo.duration)));
				jsonObj.put("duration",sleepInfo.duration/3600000%24 + "Сʱ" + sleepInfo.duration/60000%60 + "����" + sleepInfo.duration/1000%60 + "��");
				if(sleepInfo.type==0)
					jsonObj.put("type", "ǳ˯��");
				else if(sleepInfo.type==1)
					jsonObj.put("type", "��˯��");
			
				jsonArray.add(jsonObj);
			}
			return jsonArray;
		}
		//�����˯������ͳ��(ÿ�����˯��ʱ�䣬ǳ˯��ʱ�䣬˯����ʱ��)
		else{
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
			long startTime = formater.parse(request.getParameter("startTime")).getTime();
			long endTime = formater.parse(request.getParameter("endTime")).getTime();
	
			List<SleepInfo> sleepInfoList = sleepService.getSleepInfo(deviceId, startTime, endTime);
			
			List<String> dateList = new ArrayList<String>();
			Date et = new Date(endTime);	
			Date st = new Date(startTime);
			for(Date d=st;d.getTime()<=et.getTime();d.setTime(d.getTime()+24*3600*1000)){
				dateList.add(formater.format(d));
			}	
			
			int day = (int) ((endTime-startTime) / 1000 / 60 / 60 / 24) + 1;
			int deep[] = new int[day];
			int light[] = new int[day];
			for(SleepInfo sleepInfo:sleepInfoList){
				if(sleepInfo.type==2||sleepInfo.type==3)
					continue;
				int index = dateList.indexOf(formater.format(sleepInfo.getStartTime()));
				if(sleepInfo.type==0){
					light[index] += sleepInfo.getDuration();
				}
				else if(sleepInfo.type==1){
					deep[index] += sleepInfo.getDuration();
				}
			}
			for(int i=0;i<day;i++){
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("date", dateList.get(i));
				jsonObj.put("light",light[i]/3600000%24 + "Сʱ" + light[i]/60000%60 + "����" + light[i]/1000%60 + "��");
				jsonObj.put("deep",deep[i]/3600000%24 + "Сʱ" + deep[i]/60000%60 + "����" + deep[i]/1000%60 + "��");
				jsonObj.put("total", (light[i]+deep[i])/3600000%24 + "Сʱ" + (light[i]+deep[i])/60000%60 + "����" + (light[i]+deep[i])/1000%60 + "��");
				jsonArray.add(jsonObj);
			}
			return jsonArray;
		}
		
	}

}
