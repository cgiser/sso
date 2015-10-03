package com.cgiser.sso.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class DeviceGameDao {
	private static final Logger logger = LoggerFactory
	.getLogger(DeviceGameDao.class);
	private JdbcTemplate userJdbcTemplate;
	public List<Map<String,Object>> getUserGameByGameIden(String gameIden) {
		if (StringUtils.isEmpty(gameIden))
			return null;
		List<Map<String,Object>> hm = new ArrayList<Map<String,Object>>();
		String sql = "select * from t_cgiser_game_user where  gameiden=?";
		String[] para = new String[1];
		para[0] = StringUtils.trim(gameIden);
		hm = userJdbcTemplate.queryForList(sql,para);
		return hm;
	}
	public int updateRoleNumByUserIden(String gameIden,String userIden,Long serverId,int roleNum) {
		String insertSql = "update t_cgiser_game_user set rolenum = "+roleNum+" where USERIDEN =? and gameIden= ? and serverid= ?";
		String[] para = new String[3];
		para[0] = StringUtils.trim(userIden);
		para[1] = StringUtils.trim(gameIden);
		para[2] = String.valueOf(serverId);
		return userJdbcTemplate.update(insertSql,para);
	}
	public Map<String,Object> getUserGameByGameIdenUserIdenServerId(String gameIden,String userIden,Long serverId) {
		if (StringUtils.isEmpty(gameIden))
			return null;
		Map hm = new HashMap();
		String sql = "select * from t_cgiser_game_user where USERIDEN =? and GAMEIDEN= ? and serverid= ?";
		String[] para = new String[3];
		para[0] = StringUtils.trim(userIden);
		para[1] = StringUtils.trim(gameIden);
		para[2] = String.valueOf(serverId);
		hm = userJdbcTemplate.queryForMap(sql, para);
		return hm;
	}
	public Long saveDeviceGame(String gameName, String gameIden,
			String userIden, Long serverId){
		Long id = new Long(0);
        //String sql = "select USERID,USERIDEN,USERNAME,CREATETIME,UDID,ISACTIVE,STATE from t_user_user where STATE = 1 and username=?";
        final String insertSql = "insert into t_cgiser_game_user(GAMEIDEN,GAMENAME,USERIDEN,serverid)values('"+gameIden+"','"+gameName+"','"+userIden+"',"+serverId+")";
        long degid;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        userJdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(insertSql.toString());
                return ps;
            }

        }, keyHolder);
        try {
        	degid = keyHolder.getKey().longValue();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            degid = 0;
        }
        if (degid > 0) {
            id = new Long(degid);
        } else {
            return 0l;
        }
        return id;
		
	}
	public Map<String,Object> getDeviceGameByUserIdenGameIden(String userIden,String gameIden) {
		if (StringUtils.isEmpty(userIden)||StringUtils.isEmpty(gameIden))
			return null;
		Map hm = new HashMap();
		String sql = "select * from t_cgiser_game_user where USERIDEN = ? and gameiden= ? ";
		String[] para = new String[2];
		para[0] = StringUtils.trim(userIden);
		para[1] = StringUtils.trim(gameIden);
		hm = userJdbcTemplate.queryForMap(sql, para);
		return hm;
	}

	public JdbcTemplate getUserJdbcTemplate() {
		return userJdbcTemplate;
	}

	public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
		this.userJdbcTemplate = userJdbcTemplate;
	}
}
