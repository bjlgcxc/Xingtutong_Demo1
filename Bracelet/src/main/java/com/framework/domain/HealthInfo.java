package com.framework.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class HealthInfo implements Serializable{
	
	private Integer deviceId;
	private String mac;
	private Long belongDate;
	private Integer calorie;
	private Integer heartRateSize;
	private Integer humidity;
	private Integer mileage;
	private Integer press;
	private Integer sleepTime;
	private Integer step;
	private Integer surfaceTem;
	private Integer temperature;
	
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
	public Long getBelongDate() {
		return belongDate;
	}
	public void setBelongDate(Long belongDate) {
		this.belongDate = belongDate;
	}
	public Integer getCalorie() {
		return calorie;
	}
	public void setCalorie(Integer calorie) {
		this.calorie = calorie;
	}
	public Integer getHeartRateSize() {
		return heartRateSize;
	}
	public void setHeartRateSize(Integer heartRateSize) {
		this.heartRateSize = heartRateSize;
	}
	public Integer getHumidity() {
		return humidity;
	}
	public void setHumidity(Integer humidity) {
		this.humidity = humidity;
	}
	public Integer getMileage() {
		return mileage;
	}
	public void setMileage(Integer mileage) {
		this.mileage = mileage;
	}
	public Integer getPress() {
		return press;
	}
	public void setPress(Integer press) {
		this.press = press;
	}
	public Integer getSleepTime() {
		return sleepTime;
	}
	public void setSleepTime(Integer sleepTime) {
		this.sleepTime = sleepTime;
	}
	public Integer getStep() {
		return step;
	}
	public void setStep(Integer step) {
		this.step = step;
	}
	public Integer getSurfaceTem() {
		return surfaceTem;
	}
	public void setSurfaceTem(Integer surfaceTem) {
		this.surfaceTem = surfaceTem;
	}
	public Integer getTemperature() {
		return temperature;
	}
	public void setTemperature(Integer temperature) {
		this.temperature = temperature;
	}
	
}
