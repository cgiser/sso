package com.cgiser.sso.manager.impl;

import java.sql.Timestamp;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.cgiser.sso.dao.DeviceDao;
import com.cgiser.sso.manager.DeviceManager;
import com.cgiser.sso.model.Device;
import com.cgiser.sso.model.DeviceType;

public class DeviceMangerImpl implements DeviceManager {
	private DeviceDao deviceDao;
	@Override
	public Device getDeviceById(String udid) {
		// TODO Auto-generated method stub
		Map<String,Object> map = deviceDao.getDeviceById(udid);
		
		return MapToDevice(map);
	}
	public DeviceDao getDeviceDao() {
		return deviceDao;
	}
	public void setDeviceDao(DeviceDao deviceDao) {
		this.deviceDao = deviceDao;
	}
	public Device MapToDevice(Map<String, Object> map){
		if(CollectionUtils.isEmpty(map)){
			return null;
		}
		Device device = new Device();
		device.setDeviceId(new Long(map.get("DEVICEID").toString()));
		device.setDeviceIden(map.get("DEVICEIDEN").toString());
		device.setCreateTime((Timestamp)map.get("CREATETIME"));
		device.setUdid(map.get("UDID").toString());
		device.setOsType(map.get("OSTYPE").toString());
		device.setIsActive(Integer.parseInt(map.get("ISACTIVE").toString()));
		device.setUserIden((String)map.get("USERIDEN"));
		device.setState(Integer.parseInt(map.get("STATE").toString()));
		return device;
	}
	@Override
	public Long saveDevice(String udid, String userIden, String osType) {
		// TODO Auto-generated method stub
		return  deviceDao.saveDevice(udid, userIden, osType);
	}
	@Override
	public int updateDeviceUserIden(String udid, String userIden) {
		// TODO Auto-generated method stub
		return deviceDao.updateDeviceUserIden(udid, userIden);
	}
}
