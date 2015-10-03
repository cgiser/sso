package com.cgiser.sso.manager.impl;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.cgiser.sso.manager.EmailManager;
import com.cgiser.sso.model.User;

public class EmailManagerImpl implements EmailManager {
	private String from;
	private String host;
	private String user;
	private String pwd;
	@Override
	public void sendMail(String to, String msgText,String subject) {
//		String from = "cgiser@cgiser.com";
//		String host = "smtp.exmail.qq.com";
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.auth", "true");
		Session session = Session.getInstance(props, null);

		try {
			Transport transport = session.getTransport();
			transport.connect(host, user, pwd);
			// create a message
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			InternetAddress[] address = { new InternetAddress(to) };
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(subject);
			msg.setSubject(subject, "utf-8");
			msg.setSentDate(new Date());
			// If the desired charset is known, you can use
			// setText(text, charset)
//			msg.setText(msgText);
			msg.setText(msgText,"utf-8");
			transport.sendMessage(msg, address);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	@Override
	public void sendActiveSucessEmailPwd(String to, User user,String pwd) {
		this.sendMail(to, "亲爱的用户:"+user.getEmail()+"，您好，感谢您的注册，您的登录密码是："+pwd+"请牢记密码！","月光宝盒绑定邮箱邮件");
		
	}
	@Override
	public void sendActiveSucessEmail(String to, User user) {
		this.sendMail(to, "亲爱的用户:"+user.getEmail()+"，您好，感谢您的注册！","月光宝盒修改绑定邮箱邮件");
		
	}
	@Override
	public void sendGetPasswordEmail(String to, User user, String pwd) {
		this.sendMail(to, "亲爱的用户:"+user.getEmail()+"，您好，您的密码已经重置为："+pwd+"请牢记密码！","月光宝盒找回密码邮件");
		
	}
	public static void main(String[] args) {
		EmailManagerImpl em = new EmailManagerImpl();
		em.from = "cgiser@cgiser.com";
		em.host = "smtp.exmail.qq.com";
		em.user = "cgiser@cgiser.com";
		em.pwd = "yh13739641298";
		em.sendMail("282848081@qq.com", "您好，您的密码已经重置为请牢记密码","绑定邮箱");
//		System.out.println(RandomStringUtils.randomAlphabetic(6));
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}





}
