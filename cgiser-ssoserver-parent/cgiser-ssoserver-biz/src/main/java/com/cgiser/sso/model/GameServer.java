package com.cgiser.sso.model;

public class GameServer {
	private Long serverId;
	private String gameIden;
	private String serverName;
	private String serverIp;
	private int serverPort;
	private String desc;
	private String chatServerIp;
	private int chatServerPort;
	private int state;
	private int isNew;
	private int roleNum;
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getServerIp() {
		return serverIp;
	}
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Long getServerId() {
		return serverId;
	}
	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}
	public String getGameIden() {
		return gameIden;
	}
	public void setGameIden(String gameIden) {
		this.gameIden = gameIden;
	}
	public int getServerPort() {
		return serverPort;
	}
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	public String getChatServerIp() {
		return chatServerIp;
	}
	public void setChatServerIp(String chatServerIp) {
		this.chatServerIp = chatServerIp;
	}
	public int getChatServerPort() {
		return chatServerPort;
	}
	public void setChatServerPort(int chatServerPort) {
		this.chatServerPort = chatServerPort;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getIsNew() {
		return isNew;
	}
	public void setIsNew(int isNew) {
		this.isNew = isNew;
	}
	public int getRoleNum() {
		return roleNum;
	}
	public void setRoleNum(int roleNum) {
		this.roleNum = roleNum;
	}
	
}
