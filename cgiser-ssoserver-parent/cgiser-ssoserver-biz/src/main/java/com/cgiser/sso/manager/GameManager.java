package com.cgiser.sso.manager;

import java.util.List;

import com.cgiser.sso.model.Game;
import com.cgiser.sso.model.GameServer;

public interface GameManager {
	/**
	 * 根据游戏加密ID获取游戏
	 * @param gameIden
	 * @return 游戏信息
	 */
	public Game getGameByGameIden(String gameIden);
	/**
	 * 根据游戏名称获取游戏
	 * @param gameIden
	 * @return 游戏信息
	 */
	public Game getGameByGameName(String gameName);
	/**
	 * 根据游戏加密ID获取游戏的服务器列表
	 * @param gameIden
	 * @return 游戏服务器列表
	 */
	public List<GameServer> getGameServerList(String gameIden);
	/**
	 * 获取游戏的服务器列表
	 * @param gameIden
	 * @return 游戏服务器列表
	 */
	public List<GameServer> getAllGameServerList();
}
