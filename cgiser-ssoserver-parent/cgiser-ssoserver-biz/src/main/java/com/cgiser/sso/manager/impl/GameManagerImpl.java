package com.cgiser.sso.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.cgiser.sso.dao.GameDao;
import com.cgiser.sso.manager.GameManager;
import com.cgiser.sso.model.Game;
import com.cgiser.sso.model.GameServer;

public class GameManagerImpl implements GameManager {
	GameDao gameDao;
	@Override
	public Game getGameByGameIden(String gameIden) {
		Game game = MapToGame(gameDao.getGameByGameIden(gameIden));
		if(game!=null){
			game.setGameServerList(getGameServerList(gameIden));
		}
		return game;
	}
	@Override
	public Game getGameByGameName(String gameName) {
		Game game = MapToGame(gameDao.getGameByGameName(gameName));
		if(game!=null){
			game.setGameServerList(getGameServerList(game.getGameIden()));
		}
		return game;
	}
	@Override
	public List<GameServer> getGameServerList(String gameIden) {
		List<Map<String,Object>> list = gameDao.getGameServerList(gameIden);
		return MapToGameServerList(list);
	}
	
	public GameDao getGameDao() {
		return gameDao;
	}

	public void setGameDao(GameDao gameDao) {
		this.gameDao = gameDao;
	}
	protected Game MapToGame(Map<String,Object> map){
		if(CollectionUtils.isEmpty(map)){
			return null;
		}
		Game game = new Game();
		game.setGameId(new Long(map.get("GAMEID").toString()));
		game.setGameIden(map.get("GAMEIDEN").toString());
		game.setGameName(map.get("GAMENAME").toString());
		game.setLocale(map.get("LOCALE").toString());
		game.setState(Integer.parseInt(map.get("STATE").toString()));
		game.setVersion(map.get("VERSION").toString());
		game.setUpdate(Integer.parseInt(map.get("UPDATE").toString()));
		return game;
	}
	protected List<GameServer> MapToGameServerList(List<Map<String,Object>> list){
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		List<GameServer> listResult = new ArrayList<GameServer>();
		Map<String,Object> map = null;
		GameServer server = null;
		for(int i=0;i<list.size();i++){
			map = list.get(i);
			if(CollectionUtils.isEmpty(map)){
				continue;
			}else{
				server = new GameServer();
				server.setServerId(new Long(map.get("SERVERID").toString()));
				server.setGameIden((String)map.get("GAMEIDEN"));
				server.setServerName((String)map.get("SERVERNAME"));
				server.setServerIp((String)map.get("SERVERIP"));
				server.setServerPort(Integer.parseInt((String)map.get("SERVERPORT")));
				server.setChatServerIp((String)map.get("CHATSERVERIP"));
				server.setChatServerPort(map.get("CHATSERVERPORT")==null?0:Integer.parseInt((String)map.get("CHATSERVERPORT")));
				server.setDesc((String)map.get("SERVERDESC"));
				server.setState(Integer.parseInt((String)map.get("STATE")));
				server.setIsNew(Integer.parseInt((String)map.get("ISNEW")));
				listResult.add(server);
			}
			
		}
		return listResult;
	}
	@Override
	public List<GameServer> getAllGameServerList() {
		List<Map<String,Object>> list = gameDao.getAllGameServerList();
		return MapToGameServerList(list);
	}

	
}
