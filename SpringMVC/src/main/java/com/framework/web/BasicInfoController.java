package com.framework.web;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.framework.domain.BasicInfo;
import com.framework.service.BasicInfoService;

@Controller
public class BasicInfoController {

	@Autowired
	BasicInfoService basicInfoService;
	
	/*
	 * ��ȡ�û�������Ϣ
	 */
	@ResponseBody
	@RequestMapping(value="/basic/{deviceId}/getBasicInfo",method = RequestMethod.GET)
	public BasicInfo getBasicInfo(HttpServletRequest request,@PathVariable int deviceId){
		return basicInfoService.getBasicInfo(deviceId);
	}
	
}
