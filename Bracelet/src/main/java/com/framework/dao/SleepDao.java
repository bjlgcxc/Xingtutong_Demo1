package com.framework.dao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.framework.domain.SleepInfo;

@Repository
public class SleepDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void insertSleepInfoArray(SleepInfo[] sleepInfoArray){
		String sql = " insert into t_sleep(deviceId,mac,duration,startTime,type) " +
				"values(?,?,?,?,?) ";
		List<Object[]> batchArgs = new ArrayList<Object[]>();
		for(SleepInfo sleepInfo:sleepInfoArray){
			Object args[] = new Object[]{sleepInfo.getDeviceId(),sleepInfo.getMac(),sleepInfo.getDuration(),
					sleepInfo.getStartTime(),sleepInfo.getType()};
			batchArgs.add(args);
		}
		
		jdbcTemplate.batchUpdate(sql, batchArgs);
	}
	
	public List<SleepInfo> getSleepInfo(int deviceId,long startTime,long endTime){
		String sql = " select startTime,duration,type from t_sleep where deviceId=? and startTime>=? and startTime<=? ";
		Object[] args = new Object[]{deviceId,startTime,endTime};
		return jdbcTemplate.query(sql, args,new BeanPropertyRowMapper<SleepInfo>(SleepInfo.class));
	}
	public List<SleepInfo> getSleepInfo(int deviceId,String mac,long startTime,long endTime){
		String sql = " select startTime,duration,type from t_sleep where deviceId=? and mac=? and startTime>=? and startTime<=? ";
		Object[] args = new Object[]{deviceId,mac,startTime,endTime};
		return jdbcTemplate.query(sql, args,new BeanPropertyRowMapper<SleepInfo>(SleepInfo.class));
	}

}
