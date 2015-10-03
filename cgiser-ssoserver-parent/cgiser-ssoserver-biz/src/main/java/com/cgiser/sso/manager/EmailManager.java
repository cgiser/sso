package com.cgiser.sso.manager;

import com.cgiser.sso.model.User;

public interface EmailManager {
	public void sendMail(String to,String msg,String subject);
	
	public void sendActiveSucessEmailPwd(String to,User user,String pwd);
	public void sendActiveSucessEmail(String to,User user);
	public void sendGetPasswordEmail(String to,User user,String pwd);
}
