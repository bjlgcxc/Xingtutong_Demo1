package com.framework.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SportInfo implements Serializable{
	
	public Integer deviceId;
	public Integer calorie;
	public Integer countStep;
	public Long startTime;
	public Long endTime;
	public Integer duration;
	public Integer type;
	
	public Integer getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}
	public Integer getCalorie() {
		return calorie;
	}
	public void setCalorie(Integer calorie) {
		this.calorie = calorie;
	}
	public Integer getCountStep() {
		return countStep;
	}
	public void setCountStep(Integer countStep) {
		this.countStep = countStep;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}	
	
}
