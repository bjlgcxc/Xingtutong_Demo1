package com.framework.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.framework.dao.InstructionDao;
import com.framework.domain.Instruction;

@Service
public class InstructionService {
	
	@Autowired
	InstructionDao instructionDao;
	
	public List<Instruction> getInstructions(int deviceId){
		return instructionDao.queryInstructions(deviceId);
	}
	
	public void updateInstructionSendInfo(Instruction instruction){
		instructionDao.updateInstructionSendInfo(instruction);
	}
	
	public void addInstruction(Instruction instruction){
		instructionDao.insertInstruction(instruction);
	}
	
}
