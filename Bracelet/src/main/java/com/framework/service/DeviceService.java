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
	
	public boolean hasMatchDevice(String imei){
		return deviceDao.queryDeviceMatchCount(imei) > 0;
	}
	public String getDeviceMac(int id){
		return deviceDao.queryDeviceMac(id);
	}
	public String getDeviceMac(String imei){
		return deviceDao.queryDeviceMac(imei);
	}
	public int getDeviceId(String imei){
		return deviceDao.queryDeviceId(imei);
	}
	public void addDeviceInfo(DeviceInfo deviceInfo){
		deviceDao.insertDeviceInfo(deviceInfo);
	}
	public void updateDevicetInfo(DeviceInfo deviceInfo){
		deviceDao.updateDeviceInfo(deviceInfo);
	}
	public void updateConnectTime(DeviceInfo deviceInfo){
		deviceDao.updateConnectTime(deviceInfo);
	}
	
	public List<DeviceInfo> getDeviceInfo(String deviceId,String imei,String mac){
		return deviceDao.getDeviceInfo(deviceId,imei, mac);
	}

}
