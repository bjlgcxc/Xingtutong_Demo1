package com.framework.domain;

import java.io.Serializable;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class Instruction implements Serializable{
	
	Integer deviceId;
	Integer isSend;
	Integer braceletInterval;
	Integer braceletUpload;
	Integer locationInterval;
	Integer locationUpload;
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
	public Integer getBraceletInterval() {
		return braceletInterval;
	}
	public void setBraceletInterval(Integer braceletInterval) {
		this.braceletInterval = braceletInterval;
	}
	public Integer getBraceletUpload() {
		return braceletUpload;
	}
	public void setBraceletUpload(Integer braceletUpload) {
		this.braceletUpload = braceletUpload;
	}
	public Integer getLocationInterval() {
		return locationInterval;
	}
	public void setLocationInterval(Integer locationInterval) {
		this.locationInterval = locationInterval;
	}
	public Integer getLocationUpload() {
		return locationUpload;
	}
	public void setLocationUpload(Integer locationUpload) {
		this.locationUpload = locationUpload;
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
