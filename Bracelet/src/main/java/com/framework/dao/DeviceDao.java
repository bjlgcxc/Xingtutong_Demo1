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
	
	// 查询设备imei匹配个数
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
	
	// 插入设备信息 imei,connectTime
	public void insertDeviceInfo(DeviceInfo deviceInfo){
		String sqlStr = " insert into t_device(imei,connectTime) values(?,?) ";
		Object args[] = new Object[]{deviceInfo.getImei(),deviceInfo.getConnectTime()};	
		jdbcTemplate.update(sqlStr,args);
	}
	
	// 更新设备信息 
	public void updateDeviceInfo(DeviceInfo deviceInfo){
		String sqlStr = " update t_device set mac=?,connectTime=? where id=? ";
		Object args[] = new Object[]{deviceInfo.getMac(),deviceInfo.getConnectTime(),deviceInfo.getId()};
		jdbcTemplate.update(sqlStr,args);
	}
	
	// 更新手机连接后台的时间
	public void updateConnectTime(DeviceInfo deviceInfo){
		String sqlStr = " update t_device set connectTime=? where id=? ";
		Object args[] = new Object[]{deviceInfo.getConnectTime(),deviceInfo.getId()};
		jdbcTemplate.update(sqlStr, args);
	}
	
    // 获取设备信息
	public List<DeviceInfo> getDeviceInfo(String deviceId,String imei,String mac){
		String sql = " select * from t_device where 1=1 ";
		if(deviceId!=null && deviceId!=""){
			sql += " and id=" + deviceId;
		}
		if(imei!=null && imei!=""){ 
			sql += " and imei=" + "'" + imei + "'";
		}
		if(mac!=null && mac!=""){
			sql += " and mac=" + "'" + mac + "'";
		}
		
		List<DeviceInfo> result = jdbcTemplate.query(sql,new BeanPropertyRowMapper<DeviceInfo>(DeviceInfo.class));
		if(result.size()!=0)
			return result;
		else
			return null;
	}
	
}
