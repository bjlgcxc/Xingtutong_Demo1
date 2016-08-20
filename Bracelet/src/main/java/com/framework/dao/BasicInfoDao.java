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
	
	public int getMatchCount(int deviceId){
		String sql = " select count(*) from t_basic where deviceId=? ";
		Object args[] = new Object[]{deviceId};
		return jdbcTemplate.queryForInt(sql,args);
	}
	
	public void insertBasicInfo(BasicInfo basicInfo){
		String sql = " insert into t_basic(deviceId,height,weight,stepLength,sex,unit) values(?,?,?,?,?,?) ";
		Object args[] = new Object[]{basicInfo.getDeviceId(),basicInfo.getHeight(),basicInfo.getWeight(),
				basicInfo.getStepLength(),basicInfo.getSex(),basicInfo.getUnit()};
		jdbcTemplate.update(sql, args);
	}
	
	public void updateBasicInfo(BasicInfo basicInfo){
		String sql = " update t_basic set height=?,weight=?,stepLength=?,sex=?,unit=? where deviceId=? ";
		Object args[] = new Object[]{basicInfo.getHeight(),basicInfo.getWeight(),basicInfo.getStepLength(),
				basicInfo.getSex(),basicInfo.getUnit(),basicInfo.getDeviceId()};
		jdbcTemplate.update(sql, args);
	}
	
	// 获取最新的一条记录
	public BasicInfo queryBasicInfo(int deviceId){
		String sql = " select * from t_basic where deviceId=?";
		Object args[] = new Object[]{deviceId};
		List<BasicInfo> result = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<BasicInfo>(BasicInfo.class));
		if(result.size()!=0)
			return result.get(0);
		else 
			return null;
	}
	public BasicInfo queryBasicInfo(int deviceId,String mac){
		String sql = " select * from t_basic where deviceId=? and mac=?";
		Object args[] = new Object[]{deviceId,mac};
		List<BasicInfo> result = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<BasicInfo>(BasicInfo.class));
		if(result.size()!=0)
			return result.get(0);
		else 
			return null;
	}
}
