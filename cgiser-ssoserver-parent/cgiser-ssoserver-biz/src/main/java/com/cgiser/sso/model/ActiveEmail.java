package com.cgiser.sso.model;

import java.util.Date;

/**
 * 用户绑定Email激活类
 * @author Administrator
 *
 */
public class ActiveEmail {
	private Long id;
	private String userIden;
	private String email;
	private String activeCode;
	private Date createTime;
	private int state;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getActiveCode() {
		return activeCode;
	}
	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getUserIden() {
		return userIden;
	}
	public void setUserIden(String userIden) {
		this.userIden = userIden;
	}
}
