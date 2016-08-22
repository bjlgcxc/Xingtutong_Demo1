package com.framework.dao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.framework.domain.HeartRate;

@Repository
public class HeartRateDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void insertHeartRateArray(HeartRate[] heartRateArray){
		String sql = " insert into t_heartRate(deviceId,mac,measureTpye,result,size,surfaceTem,testTime,type) " +
				"values(?,?,?,?,?,?,?,?) ";
		
		List<Object[]> batchArgs = new ArrayList<Object[]>();
		for(HeartRate heartRate:heartRateArray){
			Object args[] = new Object[]{heartRate.getDeviceId(),heartRate.getMac(),heartRate.getMeasureType(),heartRate.getResult(),
					heartRate.getSize(),heartRate.getSurfaceTem(),heartRate.getTestTime(),heartRate.getType()};
			batchArgs.add(args);
		}
		
		jdbcTemplate.batchUpdate(sql,batchArgs);
	}
	
	public List<HeartRate> queryHeartRate(int deviceId,long startTime,long endTime){
		String sql = " select size,testTime from t_heartrate where deviceId=? and testTime>=? and testTime<=? ";
		Object[] args = new Object[]{deviceId,startTime,endTime};
		return jdbcTemplate.query(sql, args,new BeanPropertyRowMapper<HeartRate>(HeartRate.class));
	}
	public List<HeartRate> queryHeartRate(int deviceId,String mac,long startTime,long endTime){
		String sql = " select size,testTime from t_heartrate where deviceId=? and mac=? and testTime>=? and testTime<=? ";
		Object[] args = new Object[]{deviceId,mac,startTime,endTime};
		return jdbcTemplate.query(sql, args,new BeanPropertyRowMapper<HeartRate>(HeartRate.class));
	}
	
	public List<HeartRate> querySurfaceTem(int deviceId,long startTime,long endTime){
		String sql = " select surfaceTem,testTime from t_heartrate where deviceId=? and testTime>=? and testTime<=? ";
		Object[] args = new Object[]{deviceId,startTime,endTime};
		return jdbcTemplate.query(sql, args,new BeanPropertyRowMapper<HeartRate>(HeartRate.class));
	}
	public List<HeartRate> querySurfaceTem(int deviceId,String mac,long startTime,long endTime){
		String sql = " select surfaceTem,testTime from t_heartrate where deviceId=? and mac=? and testTime>=? and testTime<=? ";
		Object[] args = new Object[]{deviceId,mac,startTime,endTime};
		return jdbcTemplate.query(sql, args,new BeanPropertyRowMapper<HeartRate>(HeartRate.class));
	}
	
}
