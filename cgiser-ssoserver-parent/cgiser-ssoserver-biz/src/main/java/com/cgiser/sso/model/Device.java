package com.cgiser.sso.model;

import java.sql.Timestamp;

public class Device implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6825118961321345224L;
	private Long deviceId;
	private String deviceIden;
	private String udid;
	private String userIden;
	private Timestamp createTime;
	private String osType;
	private int isActive;
	private int state;
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceIden() {
		return deviceIden;
	}
	public void setDeviceIden(String deviceIden) {
		this.deviceIden = deviceIden;
	}
	public String getUdid() {
		return udid;
	}
	public void setUdid(String udid) {
		this.udid = udid;
	}
	public String getUserIden() {
		return userIden;
	}
	public void setUserIden(String userIden) {
		this.userIden = userIden;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
}
