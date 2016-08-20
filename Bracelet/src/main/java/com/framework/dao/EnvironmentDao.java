package com.framework.dao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.framework.domain.Environment;

@Repository
public class EnvironmentDao {
	
	@Autowired 
	JdbcTemplate jdbcTemplate;
	
	public void insertEnvironmentArray(Environment[] environmentArray){
		String sql = " insert into t_environment(deviceId,humidity,press,temperature,testTime) " +
				"values(?,?,?,?,?) ";	
		List<Object[]> batchArgs = new ArrayList<Object[]>();
		for(Environment environment:environmentArray){
			Object args[] = new Object[]{environment.getDeviceId(),environment.getHumidity(),environment.getPress(),
					environment.getTemperature(),environment.getTestTime()};
			batchArgs.add(args);
		}
		
		jdbcTemplate.batchUpdate(sql, batchArgs);
	}
	
	public List<Environment> quertEnvironment(int deviceId,long startTime,long endTime){
		String sql = " select * from t_environment where deviceId=" + deviceId;
		if(startTime!=0){
			sql += " and testTime>=" + startTime;
		}
		if(endTime!=0){
			sql += " and testTime<=" + endTime;
		}
		
		return jdbcTemplate.query(sql,new BeanPropertyRowMapper<Environment>(Environment.class));
	}
	
	public List<Environment> queryHumidity(int deviceId,long startTime,long endTime){
		String sql = " select humidity,testTime from t_environment where deviceId=? and testTime>=? and testTime<=? ";
		Object[] args = new Object[]{deviceId,startTime,endTime};
		return jdbcTemplate.query(sql, args,new BeanPropertyRowMapper<Environment>(Environment.class));
	}
	public List<Environment> queryHumidity(int deviceId,String mac,long startTime,long endTime){
		String sql = " select humidity,testTime from t_environment where deviceId=? and mac=? and testTime>=? and testTime<=? ";
		Object[] args = new Object[]{deviceId,mac,startTime,endTime};
		return jdbcTemplate.query(sql, args,new BeanPropertyRowMapper<Environment>(Environment.class));
	}
	
	public List<Environment> queryTemperature(int deviceId,long startTime,long endTime){
		String sql = " select temperature,testTime from t_environment where deviceId=? and testTime>=? and testTime<=? ";
		Object[] args = new Object[]{deviceId,startTime,endTime};
		return jdbcTemplate.query(sql, args,new BeanPropertyRowMapper<Environment>(Environment.class));
	}
	public List<Environment> queryTemperature(int deviceId,String mac,long startTime,long endTime){
		String sql = " select temperature,testTime from t_environment where deviceId=? and mac=? and testTime>=? and testTime<=? ";
		Object[] args = new Object[]{deviceId,mac,startTime,endTime};
		return jdbcTemplate.query(sql, args,new BeanPropertyRowMapper<Environment>(Environment.class));
	}
	
	public List<Environment> queryPress(int deviceId,long startTime,long endTime){
		String sql = " select press,testTime from t_environment where deviceId=? and testTime>=? and testTime<=? ";
		Object[] args = new Object[]{deviceId,startTime,endTime};
		return jdbcTemplate.query(sql, args,new BeanPropertyRowMapper<Environment>(Environment.class));
	}
	public List<Environment> queryPress(int deviceId,String mac,long startTime,long endTime){
		String sql = " select press,testTime from t_environment where deviceId=? and mac=? and testTime>=? and testTime<=? ";
		Object[] args = new Object[]{deviceId,mac,startTime,endTime};
		return jdbcTemplate.query(sql, args,new BeanPropertyRowMapper<Environment>(Environment.class));
	}
	
}
