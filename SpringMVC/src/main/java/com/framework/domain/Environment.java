package com.framework.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Environment implements Serializable {

	Integer deviceId;
	Integer humidity;
	Float press;
	Float temperature;
	Long testTime;
	
	public Integer getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
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
