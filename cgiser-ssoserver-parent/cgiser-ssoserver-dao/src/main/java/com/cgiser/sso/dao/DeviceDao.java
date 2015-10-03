package com.cgiser.sso.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.cgiser.sso.dao.util.Passport;

public class DeviceDao {
	private static final Logger logger = LoggerFactory
			.getLogger(DeviceDao.class);
	private JdbcTemplate userJdbcTemplate;

	public Map<String, Object> getDeviceById(String udid) {
		if (StringUtils.isEmpty(udid))
			return null;
		Map hm = new HashMap();
		String sql = "select DEVICEID,DEVICEIDEN,USERIDEN,UDID,CREATETIME,OSTYPE,ISACTIVE,STATE from t_cgiser_device where STATE = 1 and udid=?";
		String[] para = new String[1];
		para[0] = StringUtils.trim(udid);
		hm = userJdbcTemplate.queryForMap(sql, para);
		return hm;

	}
	public int updateDeviceUserIden(String udid,String userIden) {
		String insertSql = "update t_cgiser_device set USERIDEN = '"+userIden+"' where udid =?";
		String[] para = new String[1];
		para[0] = udid;
		return userJdbcTemplate.update(insertSql,para);
	}
	public Long saveDevice(String udid,String userIden,String osType){
		Long deveceId;
        String deviceid = String.valueOf(getSequence("t_cgiser_device"));
        String deviceiden = Passport.passport_encrypt(deviceid);
        final String insetSqlNew = "insert into t_cgiser_device(DEVICEID,DEVICEIDEN,UDID,USERIDEN,CREATETIME,OSTYPE,ISACTIVE,STATE) values ("+deviceid+",'"+deviceiden+"','"+udid+"','"+userIden+"','"+new Timestamp(System.currentTimeMillis())+"','"+osType+"',"+1+","+1+")";
        KeyHolder keyHolderNew = new GeneratedKeyHolder();
        userJdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(insetSqlNew);
                return ps;
            }

        }, keyHolderNew);
        try {
        	deveceId = keyHolderNew.getKey().longValue();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            deveceId = 0L;
        }
        if (deveceId > 0) {
        	return deveceId;
        } else {
            return 0l;
        }
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
