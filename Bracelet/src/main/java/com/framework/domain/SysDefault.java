package com.framework.domain;

import java.io.Serializable;


@SuppressWarnings("serial")
public class SysDefault implements Serializable{

	public Integer id;
	public Integer sampleInterval;
	public Integer uploadEverytime;
	public Integer locateInterval;
	public Integer locateTimes;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSampleInterval() {
		return sampleInterval;
	}
	public void setSampleInterval(Integer sampleInterval) {
		this.sampleInterval = sampleInterval;
	}
	public Integer getUploadEverytime() {
		return uploadEverytime;
	}
	public void setUploadEverytime(Integer uploadEverytime) {
		this.uploadEverytime = uploadEverytime;
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

}
