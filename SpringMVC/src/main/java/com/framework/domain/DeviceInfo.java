package com.framework.domain;

import java.io.Serializable;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class DeviceInfo implements Serializable{
	
	public Integer id;
	public String imei;
	public String mac;
	public Timestamp connectTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public Timestamp getConnectTime() {
		return connectTime;
	}
	public void setConnectTime(Timestamp connectTime) {
		this.connectTime = connectTime;
	}
	
}
