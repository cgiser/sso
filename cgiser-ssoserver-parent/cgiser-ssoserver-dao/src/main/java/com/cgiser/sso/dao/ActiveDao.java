package com.cgiser.sso.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class ActiveDao {
	private static final Logger logger = LoggerFactory
	.getLogger(ActiveDao.class);
	private JdbcTemplate userJdbcTemplate;
	public Long addActive(String userIden,String email,String activeCode){
		Long activeId;
        final String insetSqlNew = "insert into t_cgiser_active(USERIDEN,ACTIVECODE,EMAIL,CREATETIME,STATE) values ('"+userIden+"','"+activeCode+"','"+email+"','"+new Timestamp(System.currentTimeMillis())+"',"+2+")";
        KeyHolder keyHolderNew = new GeneratedKeyHolder();
        userJdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(insetSqlNew);
                return ps;
            }

        }, keyHolderNew);
        try {
        	activeId = keyHolderNew.getKey().longValue();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            activeId = 0L;
        }
        if (activeId > 0) {
        	return activeId;
        } else {
            return 0l;
        }
	}
	public Map<String ,Object> getActiveEmail(String userIden, String email, String code){
		String sql = "SELECT * FROM t_cgiser_active WHERE USERIDEN=? AND EMAIL=? AND ACTIVECODE=? AND STATE=2";
		String[] para = new String[3];
		para[0] = userIden;
		para[1] = email;
		para[2] = code;
		return userJdbcTemplate.queryForMap(sql,para);
	}
	public Map<String ,Object> getActiveEmail(String userIden, String email){
		String sql = "SELECT * FROM t_cgiser_active WHERE USERIDEN=? AND EMAIL=? AND STATE=2";
		String[] para = new String[2];
		para[0] = userIden;
		para[1] = email;
		return userJdbcTemplate.queryForMap(sql,para);
	}
	public int updateActiveEmailState(String userIden, String email, String code,int state){
		String sql = "update t_cgiser_active set state = ? WHERE USERIDEN=? AND EMAIL=? AND ACTIVECODE=?";
		String[] para = new String[4];
		para[0] = String.valueOf(state);
		para[1] = userIden;
		para[2] = email;
		para[3] = code;
		return userJdbcTemplate.update(sql,para);
	}
	public JdbcTemplate getUserJdbcTemplate() {
		return userJdbcTemplate;
	}

	public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
		this.userJdbcTemplate = userJdbcTemplate;
	}
}
