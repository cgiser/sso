package com.cgiser.sso.manager.impl;

import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.cgiser.sso.dao.DeviceGameDao;
import com.cgiser.sso.manager.DeviceGameManager;
import com.cgiser.sso.manager.GameManager;
import com.cgiser.sso.model.DeviceGame;
import com.cgiser.sso.model.Game;

public class DeviceGameManagerImpl implements DeviceGameManager {
	DeviceGameDao deviceGameDao;
	GameManager gameManager;
	public DeviceGameDao getDeviceGameDao() {
		return deviceGameDao;
	}

	public void setDeviceGameDao(DeviceGameDao deviceGameDao) {
		this.deviceGameDao = deviceGameDao;
	}
	protected DeviceGame MapToDeviceGame(Map<String,Object> map){
		if(CollectionUtils.isEmpty(map)){
			return null;
		}
		DeviceGame deviceGame = new DeviceGame();
		deviceGame.setId(new Long(map.get("ID").toString()));
		deviceGame.setGameIden(map.get("GAMEIDEN").toString());
		deviceGame.setGameName(map.get("GAMENAME").toString());
		deviceGame.setRoleNum(Integer.parseInt(map.get("ROLENUM").toString()));
		deviceGame.setServerId(new Long(map.get("SERVERID").toString()));
		deviceGame.setUserIden(map.get("USERIDEN").toString());
		return deviceGame;
	}

	@Override
	public DeviceGame getUserGame(String gameIden, String userIden, Long serverId) {
		// TODO Auto-generated method stub
		return MapToDeviceGame(deviceGameDao.getUserGameByGameIdenUserIdenServerId(gameIden, userIden, serverId));
	}

	@Override
	public Long saveUserGame(String gameName, String gameIden, String userIden,
			Long serverId) {
		// TODO Auto-generated method stub
		return deviceGameDao.saveDeviceGame(gameName, gameIden, userIden, serverId);
	}

	@Override
	public int updateRoleNum(String gameIden, String userIden, Long serverId,
			int roleNum) {
		DeviceGame deviceGame = this.getUserGame(gameIden, userIden, serverId);
		if(deviceGame==null){
			Game game = gameManager.getGameByGameIden(gameIden);
			if(game!=null){
				Long id = this.saveUserGame(game.getGameName(), gameIden, userIden, serverId);
				if(id>0){
					return deviceGameDao.updateRoleNumByUserIden(gameIden, userIden, serverId, roleNum);
				}else{
					return 0;
				}
			}else{
				return 0;
			}
			
		}else{
			return deviceGameDao.updateRoleNumByUserIden(gameIden, userIden, serverId, roleNum);
		}
	}

	public GameManager getGameManager() {
		return gameManager;
	}

	public void setGameManager(GameManager gameManager) {
		this.gameManager = gameManager;
	}
	
}
