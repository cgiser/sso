package com.cgiser.sso.model;

public class DeviceGame {
	private Long id;
	private String gameIden;
	private String gameName;
	private String userIden;
	private Long serverId;
	private int roleNum;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getGameIden() {
		return gameIden;
	}
	public void setGameIden(String gameIden) {
		this.gameIden = gameIden;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public String getUserIden() {
		return userIden;
	}
	public void setUserIden(String userIden) {
		this.userIden = userIden;
	}
	public Long getServerId() {
		return serverId;
	}
	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}
	public int getRoleNum() {
		return roleNum;
	}
	public void setRoleNum(int roleNum) {
		this.roleNum = roleNum;
	}
}
