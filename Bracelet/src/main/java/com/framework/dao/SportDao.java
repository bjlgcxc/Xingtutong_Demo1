package com.framework.dao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.framework.domain.SportInfo;

@Repository
public class SportDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void insertSportInfo(SportInfo[] sportInfoArray){
		String sql = " insert into t_sport(deviceId,calorie,countStep,startTime,endTime,duration,type) " +
				"values(?,?,?,?,?,?,?) ";
		List<Object[]> batchArgs = new ArrayList<Object[]>();
		for(SportInfo sportInfo:sportInfoArray){
			Object args[] = new Object[]{sportInfo.getDeviceId(),sportInfo.getCalorie(),sportInfo.getCountStep(),
					sportInfo.getStartTime(),sportInfo.getEndTime(),sportInfo.getDuration(),sportInfo.getType()};
			batchArgs.add(args);
		}
		
		jdbcTemplate.batchUpdate(sql, batchArgs);
	}
	
	public List<SportInfo> getSportInfoInfo(int deviceId,long startTime,long endTime){
		String sql = " select calorie,countStep,startTime,endTime,duration,type from t_sport where deviceId=? and startTime>=? and startTime<=? ";
		Object[] args = new Object[]{deviceId,startTime,endTime};
		return jdbcTemplate.query(sql, args,new BeanPropertyRowMapper<SportInfo>(SportInfo.class));
	}
	public List<SportInfo> getSportInfoInfo(int deviceId,String mac,long startTime,long endTime){
		String sql = " select calorie,countStep,startTime,endTime,duration,type from t_sport where deviceId=? and mac=? and startTime>=? and startTime<=? ";
		Object[] args = new Object[]{deviceId,mac,startTime,endTime};
		return jdbcTemplate.query(sql, args,new BeanPropertyRowMapper<SportInfo>(SportInfo.class));
	}


}
