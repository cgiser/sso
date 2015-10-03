package com.cgiser.sso.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class GameDao {
	private static final Logger logger = LoggerFactory.getLogger(GameDao.class);
	private JdbcTemplate userJdbcTemplate;
	public Map<String,Object> getGameByGameIden(String gameIden) {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(gameIden))
            return null;
        Map hm = new HashMap();
        String sql = "select * from t_cgiser_game where STATE = 1 and GAMEIDEN=?";
        String[] para = new String[1];
        para[0] = StringUtils.trim(gameIden);
        hm = userJdbcTemplate.queryForMap(sql, para);
        return hm;
	}
	public Map<String,Object> getGameByGameName(String gameName) {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(gameName))
            return null;
        Map hm = new HashMap();
        String sql = "select * from t_cgiser_game where STATE = 1 and GAMENAME=?";
        String[] para = new String[1];
        para[0] = StringUtils.trim(gameName);
        hm = userJdbcTemplate.queryForMap(sql, para);
        return hm;
	}
	public List<Map<String,Object>> getGameServerList(String gameIden) {
		if (StringUtils.isEmpty(gameIden))
            return null;
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        String sql = "select * from t_cgiser_gameserver where STATE = 1 and GAMEIDEN=?";
        String[] para = new String[1];
        para[0] = StringUtils.trim(gameIden);
        list = userJdbcTemplate.queryForList(sql, para);
        return list;
	}
	public List<Map<String,Object>> getAllGameServerList() {
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        String sql = "select * from t_cgiser_gameserver where STATE = 1";
        list = userJdbcTemplate.queryForList(sql);
        return list;
	}
	public JdbcTemplate getUserJdbcTemplate() {
		return userJdbcTemplate;
	}
	public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
		this.userJdbcTemplate = userJdbcTemplate;
	}
}
