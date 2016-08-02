package com.framework.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.framework.domain.BraceletInfo;

@Repository
public class BraceletDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	// 查询手环mac匹配个数
	public int getBraceletMatchCount(String mac){
		String sqlStr = " select count(*) from t_bracelet where mac=? ";
		return jdbcTemplate.queryForInt(sqlStr,new Object[]{mac});
	}
	
	// 插入手环信息
	public void insertBraceletInfo(BraceletInfo braceletInfo){
		String sqlStr = " insert into t_bracelet(mac,name,braceletGeneration,braceletStyle,braceletType,firmwareVersion) " +
				"values(?,?,?,?,?,?) ";
		Object args[] = new Object[]{braceletInfo.getMac(),braceletInfo.getName(),braceletInfo.getBraceletGeneration(),
				braceletInfo.getBraceletStyle(),braceletInfo.getBraceletType(),braceletInfo.getFirmwareVersion()};
		jdbcTemplate.update(sqlStr, args);
	}
	
	// 更新手环信息
	public void updateBraceletInfo(BraceletInfo braceletInfo){
		String sqlStr = "update t_bracelet set name=?,braceletGeneration=?,braceletStyle=?,braceletType=?,firmwareVersion=? where mac=? ";
		Object args[] = new Object[]{braceletInfo.getName(),braceletInfo.getBraceletGeneration(),braceletInfo.getBraceletStyle()
				,braceletInfo.getBraceletType(),braceletInfo.getFirmwareVersion(),braceletInfo.getMac()};
		jdbcTemplate.update(sqlStr, args);
	}
	
	// 更新手环状态信息
	public void updateBraceletStateInfo(BraceletInfo braceletInfo){
		String sqlStr = "update t_bracelet set batteryLevel=?,batteryState=? where mac=? ";
		Object args[] = new Object[]{braceletInfo.getBatteryLevel(),braceletInfo.getBatteryState(),braceletInfo.getMac()};
		jdbcTemplate.update(sqlStr,args);
	}
	
	// 获取手环信息
	public BraceletInfo queryBraceletInfo(String mac){
		String sql = " select * from t_bracelet where mac=? ";
		Object args[] = new Object[]{mac};
		List<BraceletInfo> result = jdbcTemplate.query(sql,args,new BeanPropertyRowMapper<BraceletInfo>(BraceletInfo.class));
		if(result.size()!=0)
			return result.get(0);
		else
			return null;
	}
	// 获取手环信息
	public List<BraceletInfo> queryBraceletInfo(String mac,String deviceName,String deviceAlias){
		String sql = " select * from t_bracelet where 1=1";
		if(mac!=null && mac!=""){
			sql += " and mac='" + mac + "'";
 		}
		if(deviceName!=null && deviceName!=""){
			sql += " and name like '%" + deviceName + "%'";
		}
		if(deviceAlias!=null && deviceAlias!=""){
			sql += " and alias like '%" + deviceAlias + "%'";
		}
		List<BraceletInfo> result = jdbcTemplate.query(sql,new BeanPropertyRowMapper<BraceletInfo>(BraceletInfo.class));
		if(result.size()!=0)
			return result;
		else
			return null;
    }
}
