package com.framework.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BasicInfo implements Serializable {
	
	Integer deviceId;
	Integer height;
	Integer weight;
	Integer stepLength;
	Integer sex;
	Integer unit;
	
	public Integer getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
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
