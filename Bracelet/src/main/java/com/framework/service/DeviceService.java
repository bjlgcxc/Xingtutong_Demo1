package com.framework.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.framework.dao.DeviceDao;
import com.framework.domain.DeviceInfo;

@Service
public class DeviceService {
	
	@Autowired
	DeviceDao deviceDao;
	
	public boolean hasMatchDevice(int id){
		return deviceDao.queryDeviceMatchCount(id) > 0;
	}
	public boolean hasMatchDevice(String imei){
		return deviceDao.queryDeviceMatchCount(imei) > 0;
	}
	
	// mac
	public String getDeviceMac(int id){
		return deviceDao.queryDeviceMac(id);
	}
	public String getDeviceMac(String imei){
		return deviceDao.queryDeviceMac(imei);
	}
	
	// id
	public int getDeviceId(String imei){
		return deviceDao.queryDeviceId(imei);
	}
	
	// deviceInfo
	public void addDeviceInfo(DeviceInfo deviceInfo){
		deviceDao.insertDeviceInfo(deviceInfo);
	}
	public void updateDeviceInfo(DeviceInfo deviceInfo){
		deviceDao.updateDeviceInfo(deviceInfo);
	}
	public List<DeviceInfo> getDeviceInfo(String deviceId,String mac,String alias){
		return deviceDao.getDeviceInfo(deviceId,mac,alias);
	}
	
}
