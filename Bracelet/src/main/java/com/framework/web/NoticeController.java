package com.framework.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.framework.domain.NoticeInfo;
import com.framework.service.NoticeService;

@Controller
public class NoticeController {
	
	private static final Log log =  LogFactory.getLog(NoticeController.class);
	
	@Autowired
	NoticeService noticeService;
	
	/*
	 * 获取提醒信息(json)
	 */
	@ResponseBody
	@RequestMapping(value="/notice/{deviceId}/getNoticeInfo",method = RequestMethod.GET)
	public NoticeInfo getNoticeInfo(@PathVariable int deviceId){
		log.info("get notice info");
		return noticeService.getNoticeInfo(deviceId);
	}
	
}
