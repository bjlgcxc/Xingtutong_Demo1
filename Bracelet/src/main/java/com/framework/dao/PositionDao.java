package com.framework.dao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.framework.domain.PositionInfo;

@Repository
public class PositionDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//插入位置信息
	public void insertPositionInfo(PositionInfo positionInfo){
		String sqlStr = "insert into t_position(deviceId,time,longitude,latitude,altitude,speed,bearing,accuracy) " +
				"values(?,?,?,?,?,?,?,?)";
		Object args[] = {positionInfo.getDeviceId(),positionInfo.getTime(),positionInfo.getLongitude(),positionInfo.getLatitude(),
				positionInfo.getAltitude(),positionInfo.getSpeed(),positionInfo.getBearing(),positionInfo.getAccuracy()};
	
		jdbcTemplate.update(sqlStr, args);
	}
	
	//插入多条位置信息
	public void insertPositionInfoList(PositionInfo[] positionInfoArray){
		String sqlStr = "insert into t_position(deviceId,time,longitude,latitude,altitude,speed,bearing,accuracy) " +
				"values(?,?,?,?,?,?,?,?)";
		
		List<Object[]> batchArgs = new ArrayList<Object[]>();
		for(PositionInfo positionInfo:positionInfoArray){
			Object args[] = {positionInfo.getDeviceId(),positionInfo.getTime(),positionInfo.getLongitude(),positionInfo.getLatitude(),
					positionInfo.getAltitude(),positionInfo.getSpeed(),positionInfo.getBearing(),positionInfo.getAccuracy()};
			batchArgs.add(args);
		}
		jdbcTemplate.batchUpdate(sqlStr, batchArgs);
	}

	//查询位置信息
	public List<PositionInfo> queryPositionInfoList(int deviceId,long startTime,long endTime){
		String sql = " select * from  t_position where deviceId=? and time>=? and time<=? ";
		Object args[] = new Object[]{deviceId,startTime,endTime};
		return jdbcTemplate.query(sql,args,new BeanPropertyRowMapper<PositionInfo>(PositionInfo.class));
	}
	
}
