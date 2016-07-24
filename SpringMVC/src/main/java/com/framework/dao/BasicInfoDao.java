package com.framework.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.framework.domain.BasicInfo;

@Repository
public class BasicInfoDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void insertBasicInfo(BasicInfo basicInfo){
		String sql = " insert into t_basic(deviceId,height,weight,stepLength,sex,unit) values(?,?,?,?,?,?) ";
		Object args[] = new Object[]{basicInfo.getDeviceId(),basicInfo.getHeight(),basicInfo.getWeight(),
				basicInfo.getStepLength(),basicInfo.getSex(),basicInfo.getUnit()};
		jdbcTemplate.update(sql, args);
	}
	
	// 获取最新的一条记录
	public BasicInfo queryBasicInfo(int deviceId){
		String sql = " select * from t_basic where deviceId=? order by timestamp";
		Object args[] = new Object[]{deviceId};
		List<BasicInfo> result = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<BasicInfo>(BasicInfo.class));
		if(result.size()!=0)
			return result.get(result.size()-1);
		else 
			return null;
	}
	
}
