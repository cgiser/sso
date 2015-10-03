package com.cgiser.sso.manager;

import com.cgiser.sso.model.DeviceGame;

public interface DeviceGameManager {
	public DeviceGame getUserGame(String gameIden,String userIden,Long serverId);
	public int updateRoleNum(String gameIden,String userIden,Long serverId,int roleNum);
	public Long saveUserGame(String gameName,String gameIden,String userIden,Long serverId);
}
