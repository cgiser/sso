package com.cgiser.sso.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
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

import com.cgiser.sso.dao.util.DigestUtils;
import com.cgiser.sso.dao.util.Passport;


public class UserDao {
	private static final Logger logger = LoggerFactory.getLogger(UserDao.class);
	private JdbcTemplate userJdbcTemplate;

	public Map<String, Object> getUserByUserIden(String userIden){
        if (StringUtils.isEmpty(userIden))
            return null;
        Map hm = new HashMap();
        String sql = "select * from t_cgiser_user where STATE = 1 and useriden=?";
        String[] para = new String[1];
        para[0] = StringUtils.trim(userIden);
        hm = userJdbcTemplate.queryForMap(sql, para);
        return hm;
		
	}
	public Map<String, Object> getUserByUdid(String udid){
        if (StringUtils.isEmpty(udid))
            return null;
        String sql = "select * from t_cgiser_user where STATE = 1 and udid=?";
        String[] para = new String[1];
        para[0] = StringUtils.trim(udid);
        return userJdbcTemplate.queryForMap(sql,para);
		
	}
	public Map<String, Object> getUserByEmail(String email){
        if (StringUtils.isEmpty(email))
            return null;
        Map hm = new HashMap();
        String sql = "select * from t_cgiser_user where STATE = 1 and email=?";
        String[] para = new String[1];
        para[0] = StringUtils.trim(email);
        hm = userJdbcTemplate.queryForMap(sql, para);
        return hm;
		
	}
	public int freezeUser(String userIden){
		String insertSql = "update t_cgiser_user set state = 2  where USERIDEN= ?";
		String[] para = new String[1];
		para[0] = StringUtils.trim(userIden);
		return userJdbcTemplate.update(insertSql,para);
	}
	public int updateUserEmailPwd(String userIden, String email,String pwd){
		String sql = "update t_cgiser_user set email = ? , userpwd=? WHERE USERIDEN=?";
		String[] para = new String[3];
		para[0] = email;
		para[1] = pwd;
		para[2] = userIden;
		return userJdbcTemplate.update(sql,para);
	}
	public int updateUserEmail(String userIden, String email){
		String sql = "update t_cgiser_user set email = ? WHERE USERIDEN=?";
		String[] para = new String[2];
		para[0] = email;
		para[1] = userIden;
		return userJdbcTemplate.update(sql,para);
	}
	public int updateUserPwdByEmail(String email,String pwd){
		String sql = "update t_cgiser_user set userpwd=? WHERE email=?";
		String[] para = new String[2];
		para[0] = pwd;
		para[1] = email;
		return userJdbcTemplate.update(sql,para);
	}
	public int updateUserPwdByUserName(String userName,String pwd){
		String sql = "update t_cgiser_user set userpwd=? WHERE username=?";
		String[] para = new String[2];
		para[0] = pwd;
		para[1] = userName;
		return userJdbcTemplate.update(sql,para);
	}
	public Map<String, Object> getUserByUserName(String userName){
        if (StringUtils.isEmpty(userName))
            return null;
        Map hm = new HashMap();
        String sql = "select * from t_cgiser_user where STATE = 1 and username=?";
        String[] para = new String[1];
        para[0] = StringUtils.trim(userName);
        hm = userJdbcTemplate.queryForMap(sql, para);
        return hm;
		
	}
	public Long saveUser(String userId,String userIden,String userName,byte[] pwd,String udid,String osType,String locale,String releaseChannel,int registerType){
		Long id = new Long(0);
        //String sql = "select USERID,USERIDEN,USERNAME,CREATETIME,UDID,ISACTIVE,STATE from t_user_user where STATE = 1 and username=?";
        final String insertSql = "insert into t_cgiser_user(USERID,USERIDEN,USERNAME,USERPWD,CREATETIME,UDID,ISACTIVE,STATE,REGISTERTYPE)values("+userId+",'"+userIden+"','"+userName+"','"+DigestUtils.digest(new String(pwd)).toUpperCase()+"','"
        +new Timestamp(System.currentTimeMillis())+"','"+udid+"',"+1+","+1+","+registerType+")";
        long userid;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        userJdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(insertSql.toString());
                return ps;
            }

        }, keyHolder);
        try {
        	userid = keyHolder.getKey().longValue();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            userid = 0;
        }
        if (userid > 0) {
            id = new Long(userid);
        } else {
            return 0l;
        }
//        String deviceid = String.valueOf(getSequence("t_cgiser_device"));
//        String deviceiden = Passport.passport_encrypt(deviceid);
//        final String insetSqlNew = "insert into t_cgiser_device(DEVICEID,DEVICEIDEN,UDID,USERIDEN,CREATETIME,OSTYPE,ISACTIVE,STATE) values ("+deviceid+",'"+deviceiden+"','"+udid+"','"+userIden+"','"+new Timestamp(System.currentTimeMillis())+"','"+osType+"',"+1+","+1+")";
//        KeyHolder keyHolderNew = new GeneratedKeyHolder();
//        userJdbcTemplate.update(new PreparedStatementCreator() {
//
//            @Override
//            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//                PreparedStatement ps = con.prepareStatement(insetSqlNew);
//                return ps;
//            }
//
//        }, keyHolderNew);
        return id;
		
	}
	/**
	 * 得到该表的序列
	 * @return
	 */
    public long getSequence(String tableName){
    	String sql = "SELECT auto_increment FROM information_schema.`TABLES` WHERE TABLE_SCHEMA='User' AND TABLE_NAME='"+tableName+"'";
        Long seq = (Long) userJdbcTemplate.queryForLong(sql);
        return seq;
    	
    }
	public JdbcTemplate getUserJdbcTemplate() {
		return userJdbcTemplate;
	}
	public void setUserJdbcTemplate(JdbcTemplate userJdbcTemplate) {
		this.userJdbcTemplate = userJdbcTemplate;
	}
}
