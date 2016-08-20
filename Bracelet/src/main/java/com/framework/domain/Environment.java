package com.framework.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Environment implements Serializable {

	private Integer deviceId;
	private String mac;
	private Integer humidity;
	private Float press;
	private Float temperature;
	private Long testTime;
	
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
	public Integer getHumidity() {
		return humidity;
	}
	public void setHumidity(Integer humidity) {
		this.humidity = humidity;
	}
	public Float getPress() {
		return press;
	}
	public void setPress(Float press) {
		this.press = press;
	}
	public Float getTemperature() {
		return temperature;
	}
	public void setTemperature(Float temperature) {
		this.temperature = temperature;
	}
	public Long getTestTime() {
		return testTime;
	}
	public void setTestTime(Long testTime) {
		this.testTime = testTime;
	}
	
}
