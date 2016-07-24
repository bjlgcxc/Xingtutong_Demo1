package com.framework.web;

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
	
	@Autowired
	NoticeService noticeService;
	
	
	/*
	 * ��ȡ������Ϣ(json)
	 */
	@ResponseBody
	@RequestMapping(value="/notice/{deviceId}/getNoticeInfo",method = RequestMethod.GET)
	public NoticeInfo getNoticeInfo(@PathVariable int deviceId){
		return noticeService.getNoticeInfo(deviceId);
	}
	
}