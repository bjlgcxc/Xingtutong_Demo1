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
	
	//插入指令
	public void insertInstruction(Instruction instruction){
		String sql = " insert t_instruction(deviceId,braceletInterval,braceletUpload,locationInterval,locationUpload," +
				"locateInterval,locateTimes,teleNumber) values(?,?,?,?,?,?,?,?) ";
		Object args[] = new Object[]{instruction.getDeviceId(),instruction.getBraceletInterval(),instruction.getBraceletUpload(),
				instruction.getLocationInterval(),instruction.getLocationUpload(),instruction.getLocateInterval(),instruction.getLocateTimes(),
				instruction.getTeleNumber()};
		jdbcTemplate.update(sql, args);
	}	
	
	//更新指令发送标记
	public void updateInstructionSendInfo(Instruction instruction){
		String sql = " update t_instruction set isSend=1 where deviceId=? and timestamp=? ";
		Object args[] = new Object[]{instruction.getDeviceId(),instruction.getTimestamp()};
		jdbcTemplate.update(sql, args);
	}

}
