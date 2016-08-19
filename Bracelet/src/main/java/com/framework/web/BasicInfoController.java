package com.framework.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

	private static final Log log =  LogFactory.getLog(BasicInfoController.class);
	
	@Autowired
	BasicInfoService basicInfoService;
	
	/*
	 * 获取用户基本信息
	 */
	@ResponseBody
	@RequestMapping(value="/basic/{deviceId}/getBasicInfo",method = RequestMethod.GET)
	public BasicInfo getBasicInfo(HttpServletRequest request,@PathVariable int deviceId){
		log.info("get basic info");
		return basicInfoService.getBasicInfo(deviceId);
	}
	
}
