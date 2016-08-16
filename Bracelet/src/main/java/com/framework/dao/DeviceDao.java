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
	
	// ����id��ѯ�豸ƥ�����
	public int queryDeviceMatchCount(int id){
		String sql = " select count(*) from t_device where id=? ";
		return jdbcTemplate.queryForInt(sql,new Object[]{id});
	}
	
	// ����imei��ѯ�豸ƥ�����
	public int queryDeviceMatchCount(String imei){
		String sqlStr = " select count(*) from t_device where imei=? ";
		return jdbcTemplate.queryForInt(sqlStr,new Object[]{imei});
	}
	
	// ����id��ѯmac��ַ
	public String queryDeviceMac(int id){
		String sqlStr = " select mac from t_device where id=? ";
		return jdbcTemplate.queryForObject(sqlStr, new Object[]{id},java.lang.String.class);
	}
	
	// ����imei��ѯmac��ַ
	public String queryDeviceMac(String imei){
		String sqlStr = " select mac from t_device where imei=? ";
		return jdbcTemplate.queryForObject(sqlStr, new Object[]{imei},java.lang.String.class);
	}
	
	// ����imei��ѯ�豸id��
	public int queryDeviceId(String imei){
		String sqlStr = " select id from t_device where imei=? ";
		return jdbcTemplate.queryForInt(sqlStr,new Object[]{imei});
	}
	
	// �����豸��Ϣ imei,connectTime���Զ�����id
	public void insertDeviceInfo(DeviceInfo deviceInfo){
		String sqlStr = " insert into t_device(imei,connectTime) values(?,?) ";
		Object args[] = new Object[]{deviceInfo.getImei(),deviceInfo.getConnectTime()};	
		jdbcTemplate.update(sqlStr,args);
	}
	
	// �����豸��Ϣ(mac,alias,connectTime)
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
	
    // ��ȡ�豸��Ϣ
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
