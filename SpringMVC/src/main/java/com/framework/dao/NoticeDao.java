package com.framework.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.framework.domain.NoticeInfo;

@Repository
public class NoticeDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void insertNoticeInfo(NoticeInfo noticeInfo){
		String sql = " insert into t_notice(deviceId,heartRate,step,temp,type) values(?,?,?,?,?) ";
		Object args[] = new Object[]{noticeInfo.getDeviceId(),noticeInfo.getHeartRate(),noticeInfo.getStep()
				,noticeInfo.getTemp(),noticeInfo.getType()};
		jdbcTemplate.update(sql, args);
	}
	
	public NoticeInfo queryNoticeInfo(int deviceId){
		String sql = " select heartRate,step,temp from t_notice where deviceId=? order by timestamp";
		Object args[] = new Object[]{deviceId};
		List<NoticeInfo> result = jdbcTemplate.query(sql,args,new BeanPropertyRowMapper<NoticeInfo>(NoticeInfo.class));
		if(result.size()!=0){
			return result.get(result.size()-1);
		}
		else{
			return null;
		}
	}
	
}
