package com.cgiser.sso.manager;

import com.cgiser.sso.model.Device;

public interface DeviceManager {
	/**
	 * 根据设备ID获取设备信息
	 * @param udid
	 * @return
	 */
	public Device getDeviceById(String udid);
	
	/**
	 * 保存设备
	 * @param udid
	 * @param userIden
	 * @param osType
	 * @return
	 */
	public Long saveDevice(String udid,String userIden,String osType);
	/**
	 * 更新设备绑定的用户
	 * @param udid
	 * @param userIden
	 * @return
	 */
	public int updateDeviceUserIden(String udid,String userIden);
}
