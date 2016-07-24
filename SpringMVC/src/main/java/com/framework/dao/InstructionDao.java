package com.framework.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.framework.domain.Instruction;

@Repository
public class InstructionDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//查询指令信息
	public List<Instruction> queryInstructions(int deviceId){
		String sql = "select * from t_instruction where deviceId=? and isSend=0";
		Object args[] = {deviceId};	
		try{
			List<Instruction> instructions = jdbcTemplate.query(sql, args,
											new BeanPropertyRowMapper<Instruction>(Instruction.class));
			return instructions;
		}
		catch(DataAccessException e){
			return null;      //不存在需要发送的指令
		}
	}
	
	//更新指令发送标记
	public void updateInstructionSendInfo(Instruction instruction){
		String sql = " update t_instruction set isSend=1 where deviceId=? and timestamp=? ";
		Object args[] = new Object[]{instruction.getDeviceId(),instruction.getTimestamp()};
		jdbcTemplate.update(sql, args);
	}
	
	//插入数据采集间隔
	public void insertSampleInterval(int deviceId,int sampleInterval){
		String sql = " insert into t_instruction(deviceId,sampleInterval) values(?,?) ";
		Object args[] = new Object[]{deviceId,sampleInterval};
		jdbcTemplate.update(sql, args);
	}
	
	//插入每次上传的数据条数
	public void insertUploadEverytime(int deviceId,int uploadEverytime){
		String sql = " insert into t_instruction(deviceId,uploadEverytime) values(?,?) ";
		Object args[] = new Object[]{deviceId,uploadEverytime};
		jdbcTemplate.update(sql, args);
	}
	
	//插入紧急定位信息
	public void insertLocateInfo(int deviceId,int locateInterval,int locateTimes){
		String sql = " insert into t_instruction(deviceId,locateInterval,locateTimes) value(?,?,?) ";
		Object args[] = new Object[]{deviceId,locateInterval,locateTimes};
		jdbcTemplate.update(sql,args);
	}
	
	//插入基本信息
	public void insertBasicInfo(int deviceId,String teleNumber){
		String sql = " insert into t_instruction(deviceId,teleNumber) values(?,?)"; 
		Object args[] = new Object[]{deviceId,teleNumber};
		jdbcTemplate.update(sql,args);
	}
	
}
