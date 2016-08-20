package com.framework.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.framework.domain.HealthInfo;

@Repository
public class HealthDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void insertHealthInfo(HealthInfo healthInfo){
		String sql = " insert into t_health(deviceId,belongDate,calorie,heartRateSize,humidity,mileage,press," +
				"sleepTime,step,surfaceTem,temperature) values(?,?,?,?,?,?,?,?,?,?,?) ";
		Object args[] = new Object[]{healthInfo.getDeviceId(),healthInfo.getBelongDate(),healthInfo.getCalorie(),
			healthInfo.getHeartRateSize(),healthInfo.getHumidity(),healthInfo.getMileage(),healthInfo.getPress(),
			healthInfo.getSleepTime(),healthInfo.getStep(),healthInfo.getSurfaceTem(),healthInfo.getTemperature()};
		
		jdbcTemplate.update(sql,args);
	}
	
	public HealthInfo queryHealthInfo(int deviceId){
		String sql = "select deviceId,belongDate,calorie,heartRateSize,humidity,mileage," +
				"press,sleepTime,step,surfaceTem,temperature from t_health where deviceId=?" +
				" order by timestamp ";
		Object args[] = new Object[]{deviceId};
		List<HealthInfo> list = jdbcTemplate.query(sql,args,new BeanPropertyRowMapper<HealthInfo>(HealthInfo.class));
		if(list.size()!=0)
			return list.get(list.size()-1);
		else
			return null;
	}
	public HealthInfo queryHealthInfo(int deviceId,String mac){
		String sql = "select deviceId,belongDate,calorie,heartRateSize,humidity,mileage," +
				"press,sleepTime,step,surfaceTem,temperature from t_health where deviceId=? and mac=?" +
				" order by timestamp ";
		Object args[] = new Object[]{deviceId,mac};
		List<HealthInfo> list = jdbcTemplate.query(sql,args,new BeanPropertyRowMapper<HealthInfo>(HealthInfo.class));
		if(list.size()!=0)
			return list.get(list.size()-1);
		else
			return null;
	}
	
}
