package com.cgiser.sso.client.Impl;

import com.cgiser.sso.client.UserGameFacade;
import com.cgiser.sso.manager.DeviceGameManager;
import com.cgiser.sso.manager.UserManager;
import com.cgiser.sso.model.User;
import com.cgiser.sso.model.UserLoginResult;

public class UserGameFacadeImpl implements UserGameFacade {
	private DeviceGameManager deviceGameManager;
	private UserManager userManager;
	@Override
	public int updateRoleNum(String gameIden, String userIden, Long serverId,
			int roleNum) {
		// TODO Auto-generated method stub
		return deviceGameManager.updateRoleNum(gameIden, userIden, serverId, roleNum);
	}
	public DeviceGameManager getDeviceGameManager() {
		return deviceGameManager;
	}
	public void setDeviceGameManager(DeviceGameManager deviceGameManager) {
		this.deviceGameManager = deviceGameManager;
	}
	@Override
	public User getUserByUserIden(String userIden) {
		// TODO Auto-generated method stub
		return userManager.getUserbyUserIden(userIden);
	}
	public UserManager getUserManager() {
		return userManager;
	}
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	@Override
	public UserLoginResult login(String userName, byte[] pwd, String ip) {
		// TODO Auto-generated method stub
		return userManager.login(userName, pwd, ip);
	}

}
