package com.framework.domain;

import java.io.Serializable;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class Instruction implements Serializable{
	
	Integer deviceId;
	Integer isSend;
	Integer sampleInterval;
	Integer uploadEverytime;
	Integer locateInterval;
	Integer locateTimes;
	String teleNumber;
	Timestamp timestamp;
	
	public Integer getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}
	public Integer getIsSend() {
		return isSend;
	}
	public void setIsSend(Integer isSend) {
		this.isSend = isSend;
	}
	public Integer getSampleInterval() {
		return sampleInterval;
	}
	public void setSampleInterval(Integer sampleInterval) {
		this.sampleInterval = sampleInterval;
	}
	public Integer getUploadEverytime() {
		return uploadEverytime;
	}
	public void setUploadEverytime(Integer uploadEverytime) {
		this.uploadEverytime = uploadEverytime;
	}
	public Integer getLocateInterval() {
		return locateInterval;
	}
	public void setLocateInterval(Integer locateInterval) {
		this.locateInterval = locateInterval;
	}
	public Integer getLocateTimes() {
		return locateTimes;
	}
	public void setLocateTimes(Integer locateTimes) {
		this.locateTimes = locateTimes;
	}
	public String getTeleNumber() {
		return teleNumber;
	}
	public void setTeleNumber(String teleNumber) {
		this.teleNumber = teleNumber;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
}
