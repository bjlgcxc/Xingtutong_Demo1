package com.framework.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SystemInfo implements Serializable{

	private Integer id;
	private Integer braceletInterval;
	private Integer braceletUpload;
	private Integer locationInterval;
	private Integer locationUpload;
	private Integer locateInterval;
	private Integer locateTimes;
	private String teleNumber;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBraceletInterval() {
		return braceletInterval;
	}
	public void setBraceletInterval(Integer braceletInterval) {
		this.braceletInterval = braceletInterval;
	}
	public Integer getBraceletUpload() {
		return braceletUpload;
	}
	public void setBraceletUpload(Integer braceletUpload) {
		this.braceletUpload = braceletUpload;
	}
	public Integer getLocationInterval() {
		return locationInterval;
	}
	public void setLocationInterval(Integer locationInterval) {
		this.locationInterval = locationInterval;
	}
	public Integer getLocationUpload() {
		return locationUpload;
	}
	public void setLocationUpload(Integer locationUpload) {
		this.locationUpload = locationUpload;
	}
	public Integer getLocateInterval() {
		return locateInterval;
	}
	public void setLocateInterval(Integer locateInterval) {
		this.locateInterval = locateInterval;
	}
	public Integer getLocateTimes() {
		return locateTimes;
	}
	public void setLocateTimes(Integer locateTimes) {
		this.locateTimes = locateTimes;
	}
	public String getTeleNumber() {
		return teleNumber;
	}
	public void setTeleNumber(String teleNumber) {
		this.teleNumber = teleNumber;
	}

}
