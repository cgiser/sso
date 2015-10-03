package com.cgiser.sso.model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;

public class User implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long userId;
	private String userIden;
	private String userName;
	private String pwd;
	private String udid;
	private Timestamp createTime;
	private int isActive;
	private String email;
	private int state;
	private int registerType;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserIden() {
		return userIden;
	}
	public void setUserIden(String userIden) {
		this.userIden = userIden;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getUdid() {
		return udid;
	}
	public void setUdid(String udid) {
		this.udid = udid;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getRegisterType() {
		return registerType;
	}
	public void setRegisterType(int registerType) {
		this.registerType = registerType;
	}
	public static void main(String[] args) throws IOException {
		String sFilePath = "D://test_another_serialize.dat";

        FileOutputStream fos = null;

        ObjectOutputStream ooS = null;

        try {

            fos = new FileOutputStream(sFilePath);

            ooS = new ObjectOutputStream(fos);
            User user = new User();
            ooS.writeObject(user);

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {

            if (fos != null)

                fos.close();

            if (ooS != null) {

                ooS.close();

            }

        }
	}
}
