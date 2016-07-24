package com.framework.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ConfigInfo implements Serializable{
	
	public Integer deviceId;
	public Integer sampleInterval;
	public Integer uploadEverytime;
	public Integer locateInterval;
	public Integer locateTimes;
	public String teleNumber;
	
	public Integer getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
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

}
