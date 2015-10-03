package com.cgiser.sso.model;

public class UserGameDevice implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4025555771690114043L;
	private User user;
	private Device device;
	private Game game;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
}
