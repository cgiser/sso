package com.cgiser.sso.web.result;

import com.cgiser.sso.model.Device;
import com.cgiser.sso.model.Game;
import com.cgiser.sso.model.User;

public class ActiveResult {
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
