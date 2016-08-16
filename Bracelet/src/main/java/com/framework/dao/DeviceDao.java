package com.framework.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.framework.domain.DeviceInfo;

@Repository
public class DeviceDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	// 根据id查询设备匹配个数
	public int queryDeviceMatchCount(int id){
		String sql = " select count(*) from t_device where id=? ";
		return jdbcTemplate.queryForInt(sql,new Object[]{id});
	}
	
	// 根据imei查询设备匹配个数
	public int queryDeviceMatchCount(String imei){
		String sqlStr = " select count(*) from t_device where imei=? ";
		return jdbcTemplate.queryForInt(sqlStr,new Object[]{imei});
	}
	
	// 根据id查询mac地址
	public String queryDeviceMac(int id){
		String sqlStr = " select mac from t_device where id=? ";
		return jdbcTemplate.queryForObject(sqlStr, new Object[]{id},java.lang.String.class);
	}
	
	// 根据imei查询mac地址
	public String queryDeviceMac(String imei){
		String sqlStr = " select mac from t_device where imei=? ";
		return jdbcTemplate.queryForObject(sqlStr, new Object[]{imei},java.lang.String.class);
	}
	
	// 根据imei查询设备id号
	public int queryDeviceId(String imei){
		String sqlStr = " select id from t_device where imei=? ";
		return jdbcTemplate.queryForInt(sqlStr,new Object[]{imei});
	}
	
	// 插入设备信息 imei,connectTime并自动生成id
	public void insertDeviceInfo(DeviceInfo deviceInfo){
		String sqlStr = " insert into t_device(imei,connectTime) values(?,?) ";
		Object args[] = new Object[]{deviceInfo.getImei(),deviceInfo.getConnectTime()};	
		jdbcTemplate.update(sqlStr,args);
	}
	
	// 更新设备信息(mac,alias,connectTime)
	public void updateDeviceInfo(DeviceInfo deviceInfo){
		String sql = " update t_device set id=" + deviceInfo.getId();
		if(deviceInfo.getMac()!=null){
			sql += ",mac='" + deviceInfo.getMac() + "'";
		}
		if(deviceInfo.getAlias()!=null){
			sql += ",alias='" + deviceInfo.getAlias() + "'";
		}
		if(deviceInfo.getConnectTime()!=null){
			sql += ",connectTime='" + deviceInfo.getConnectTime() + "'";
		}
		sql += " where id=" + deviceInfo.getId();
	
		jdbcTemplate.update(sql);
	}
	
    // 获取设备信息
	public List<DeviceInfo> getDeviceInfo(String deviceId,String mac,String alias){
		String sql = " select * from t_device where 1=1 ";
		if(deviceId!=null && deviceId!=""){
			sql += " and id=" + deviceId;
		}
		if(mac!=null && mac!=""){ 
			sql += " and mac=" + "'" + mac + "'";
		}
		if(alias!=null && alias!=""){ 
			sql += " and alias like " + "'%" + alias + "%'";
		}

		List<DeviceInfo> result = jdbcTemplate.query(sql,new BeanPropertyRowMapper<DeviceInfo>(DeviceInfo.class));
		if(result.size()!=0)
			return result;
		else
			return null;
	}
	
}
