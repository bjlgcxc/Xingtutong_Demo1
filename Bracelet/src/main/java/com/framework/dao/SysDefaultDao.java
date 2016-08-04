package com.framework.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.framework.domain.SysDefault;

@Repository
public class SysDefaultDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	public SysDefault querySysDefault(){
		String sql = " select * from t_sysdefault ";
		List<SysDefault> result =  jdbcTemplate.query(sql,new BeanPropertyRowMapper<SysDefault>(SysDefault.class));
		return result.get(0);
	}
	
	public void updateSysDefault(SysDefault sysDefault){
		String sql = " update t_sysdefault set sampleInterval=? and uploadEverytime=?" +
	                             " and locateInterval=? and locateTimes=? ";
		Object args[] = new Object[]{sysDefault.getSampleInterval(),sysDefault.getUploadEverytime(),
				sysDefault.getLocateInterval(),sysDefault.getLocateTimes()};
		jdbcTemplate.update(sql, args);
	}
	
}
