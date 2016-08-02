package com.framework.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.framework.domain.ConfigInfo;

@Repository
public class ConfigDao{
		
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public int queryConfigMatchCount(int deviceId){
		String sql = " select count(*) from t_config where deviceId=? ";
		Object args[] = new Object[]{deviceId};
		return jdbcTemplate.queryForInt(sql,args);
	}
	
	public void insertConfigInfo(ConfigInfo configInfo){
		String sql = " insert into t_config(deviceId,sampleInterval,uploadEverytime,locateInterval,locateTimes,teleNumber) values(?,?,?,?,?,?) ";
		Object args[] = new Object[]{configInfo.getDeviceId(),configInfo.getSampleInterval(),configInfo.getUploadEverytime(),
				configInfo.getLocateInterval(),configInfo.getLocateTimes(),configInfo.getTeleNumber()};
		jdbcTemplate.update(sql, args);
	}
	
	public void updateConfigInfo(ConfigInfo configInfo){
	
		String sql = " update t_config set deviceId=" + configInfo.getDeviceId();
		if(configInfo.getSampleInterval()!=null){
			sql += ",sampleInterval=" + configInfo.getSampleInterval();
		}
		if(configInfo.getUploadEverytime()!=null){
			sql += ",uploadEverytime=" + configInfo.getUploadEverytime();
		}
		if(configInfo.getLocateInterval()!=null){
			sql += ",locateInterval=" + configInfo.getLocateInterval();
		}
		if(configInfo.getLocateTimes()!=null){
			sql += ",locateTimes=" + configInfo.getLocateTimes();
		}
		if(configInfo.getTeleNumber()!=null){
			sql += ",teleNumber='" + configInfo.getTeleNumber() + "'";
		}
		sql += " where deviceId=" + configInfo.getDeviceId();
	
		jdbcTemplate.update(sql);	
	}
	
	public ConfigInfo queryConfigInfo(int deviceId){
		String sql = "select deviceId,sampleInterval,uploadEverytime,locateInterval,locateTimes,teleNumber from" +
				" t_config where deviceId=? ";
		Object args[] = new Object[]{deviceId};
		List<ConfigInfo> result = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<ConfigInfo>(ConfigInfo.class));
		if(result.size()!=0)
			return result.get(0);
		else
			return null;
	}
	
}
