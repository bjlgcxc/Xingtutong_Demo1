package com.framework.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NoticeInfo implements Serializable{
	
	private Integer deviceId;
	private String mac;
	private Integer heartRate;
	private Integer step;
	private Integer temp;
	private Integer type;
	
	public Integer getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public Integer getHeartRate() {
		return heartRate;
	}
	public void setHeartRate(Integer heartRate) {
		this.heartRate = heartRate;
	}
	public Integer getStep() {
		return step;
	}
	public void setStep(Integer step) {
		this.step = step;
	}
	public Integer getTemp() {
		return temp;
	}
	public void setTemp(Integer temp) {
		this.temp = temp;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
}
