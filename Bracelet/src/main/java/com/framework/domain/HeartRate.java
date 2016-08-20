package com.framework.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class HeartRate implements Serializable {
	
	private Integer deviceId;
	private String mac;
	private Integer measureType;
	private Integer result;
	private Integer size;
	private Float surfaceTem;
	private Long testTime;
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
	public Integer getMeasureType() {
		return measureType;
	}
	public void setMeasureType(Integer measureType) {
		this.measureType = measureType;
	}
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Float getSurfaceTem() {
		return surfaceTem;
	}
	public void setSurfaceTem(Float surfaceTem) {
		this.surfaceTem = surfaceTem;
	}
	public Long getTestTime() {
		return testTime;
	}
	public void setTestTime(Long testTime) {
		this.testTime = testTime;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}

}
