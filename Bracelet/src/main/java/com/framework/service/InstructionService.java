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
	
	public void addSampleInterval(int deviceId,int sampleInterval){
		instructionDao.insertSampleInterval(deviceId, sampleInterval);
	}
	
	public void addUploadEverytime(int deviceId,int uploadEverytime){
		instructionDao.insertUploadEverytime(deviceId, uploadEverytime);
	}
	
	public void addLocateInfo(int deviceId,int locateInterval,int locateTimes){
		instructionDao.insertLocateInfo(deviceId, locateInterval, locateTimes);
	}
	
	public void addBasicInfo(int deviceId,String teleNumber){
		instructionDao.insertBasicInfo(deviceId, teleNumber);
	}
	
}
