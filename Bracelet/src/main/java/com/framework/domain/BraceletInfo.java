package com.framework.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BraceletInfo implements Serializable{
	
	private String mac;
	private String name;
	private Integer braceletGeneration;
	private Integer braceletStyle;
	private Integer braceletType;
	private String firmwareVersion;
	private Integer batteryLevel;
	private Integer batteryState;
	
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getBraceletGeneration() {
		return braceletGeneration;
	}
	public void setBraceletGeneration(Integer braceletGeneration) {
		this.braceletGeneration = braceletGeneration;
	}
	public Integer getBraceletStyle() {
		return braceletStyle;
	}
	public void setBraceletStyle(Integer braceletStyle) {
		this.braceletStyle = braceletStyle;
	}
	public Integer getBraceletType() {
		return braceletType;
	}
	public void setBraceletType(Integer braceletType) {
		this.braceletType = braceletType;
	}
	public String getFirmwareVersion() {
		return firmwareVersion;
	}
	public void setFirmwareVersion(String firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}
	public Integer getBatteryLevel() {
		return batteryLevel;
	}
	public void setBatteryLevel(Integer batteryLevel) {
		this.batteryLevel = batteryLevel;
	}
	public Integer getBatteryState() {
		return batteryState;
	}
	public void setBatteryState(Integer batteryState) {
		this.batteryState = batteryState;
	}
		
}
