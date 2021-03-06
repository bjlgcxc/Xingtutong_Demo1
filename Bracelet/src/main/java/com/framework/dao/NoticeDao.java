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
	
	public int getMatchCount(int deviceId){
		String sql = " select count(*) from t_notice where deviceId=? ";
		Object args[] = new Object[]{deviceId};
		return jdbcTemplate.queryForInt(sql,args);
	}
	
	public void insertNoticeInfo(NoticeInfo noticeInfo){
		String sql = " insert into t_notice(deviceId,mac,heartRate,step,temp,type) values(?,?,?,?,?,?) ";
		Object args[] = new Object[]{noticeInfo.getDeviceId(),noticeInfo.getMac(),noticeInfo.getHeartRate(),
				noticeInfo.getStep(),noticeInfo.getTemp(),noticeInfo.getType()};
		jdbcTemplate.update(sql, args);
	}
	
	public void updateNoticeInfo(NoticeInfo noticeInfo){
		String sql = " update t_notice set mac=?,heartRate=?,step=?,temp=?,type=? where deviceId=? ";
		Object args[] = new Object[]{noticeInfo.getMac(),noticeInfo.getHeartRate(),noticeInfo.getStep(),
				noticeInfo.getTemp(),noticeInfo.getType(),noticeInfo.getDeviceId()};
		jdbcTemplate.update(sql,args);
	}
	
	public NoticeInfo queryNoticeInfo(int deviceId){
		String sql = " select heartRate,step,temp from t_notice where deviceId=? ";
		Object args[] = new Object[]{deviceId};
		List<NoticeInfo> result = jdbcTemplate.query(sql,args,new BeanPropertyRowMapper<NoticeInfo>(NoticeInfo.class));
		if(result.size()!=0){
			return result.get(0);
		}
		else{
			return null;
		}
	}
	public NoticeInfo queryNoticeInfo(int deviceId,String mac){
		String sql = " select heartRate,step,temp from t_notice where deviceId=? and mac=?";
		Object args[] = new Object[]{deviceId,mac};
		List<NoticeInfo> result = jdbcTemplate.query(sql,args,new BeanPropertyRowMapper<NoticeInfo>(NoticeInfo.class));
		if(result.size()!=0){
			return result.get(0);
		}
		else{
			return null;
		}
	}
	
}
