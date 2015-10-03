package com.cgiser.sso.model;

import java.util.List;

public class Game implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6724681832651812309L;
	private Long gameId;
	private String gameIden;
	private String gameName;
	private int state;
	private String locale;
	private String version;
	private int update;
	private List<GameServer> gameServerList;
	public Long getGameId() {
		return gameId;
	}
	public void setGameId(Long gameId) {
		this.gameId = gameId;
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
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public List<GameServer> getGameServerList() {
		return gameServerList;
	}
	public void setGameServerList(List<GameServer> gameServerList) {
		this.gameServerList = gameServerList;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public int getUpdate() {
		return update;
	}
	public void setUpdate(int update) {
		this.update = update;
	}
}
