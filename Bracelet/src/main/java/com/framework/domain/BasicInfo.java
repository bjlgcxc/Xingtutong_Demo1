package com.framework.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BasicInfo implements Serializable {
	
	private Integer deviceId;
	private String mac;
	private Integer height;
	private Integer weight;
	private Integer stepLength;
	private Integer sex;
	private Integer unit;
	
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
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public Integer getStepLength() {
		return stepLength;
	}
	public void setStepLength(Integer stepLength) {
		this.stepLength = stepLength;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Integer getUnit() {
		return unit;
	}
	public void setUnit(Integer unit) {
		this.unit = unit;
	}
	
}
