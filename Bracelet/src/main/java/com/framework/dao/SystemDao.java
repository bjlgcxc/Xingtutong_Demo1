package com.framework.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.framework.domain.SystemInfo;

@Repository
public class SystemDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	public SystemInfo querySysDefault(){
		String sql = " select * from t_system ";
		List<SystemInfo> result = jdbcTemplate.query(sql,new BeanPropertyRowMapper<SystemInfo>(SystemInfo.class));
		return result.get(0);
	}
	
	public void updateSysDefault(SystemInfo sysDefault){
		String sql = " update t_system set braceletInterval=?,braceletUpload=?,locationInterval=?,locationUpload=?," +
				" locateInterval=?,locateTimes=? where id=1 ";
		Object args[] = new Object[]{sysDefault.getBraceletInterval(),sysDefault.getBraceletUpload(),sysDefault.getLocationInterval(),
				sysDefault.getLocationUpload(),sysDefault.getLocateInterval(),sysDefault.getLocateTimes()};
		jdbcTemplate.update(sql, args);
	}
	
}
