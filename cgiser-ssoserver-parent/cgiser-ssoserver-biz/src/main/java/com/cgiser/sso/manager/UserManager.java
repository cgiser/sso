package com.cgiser.sso.manager;

import java.util.List;

import com.cgiser.sso.model.ActiveEmail;
import com.cgiser.sso.model.ActiveResult;
import com.cgiser.sso.model.User;
import com.cgiser.sso.model.UserLoginResult;
import com.cgiser.sso.model.UserRegisterResult;

public interface UserManager {
	/**
	 * 根据用户名获取用户信息
	 * @param userName 用户名
	 * @return 返回用户信息
	 */
	public User getUserbyUserName(String userName);
	/**
	 * 根据用户Email获取用户信息
	 * @param email 用户Email
	 * @return 返回用户信息
	 */
	public User getUserbyEmail(String email);
	/**
	 * 根据用户Udid获取用户信息
	 * @param udid 用户udid
	 * @return 返回用户信息
	 */
	public User getUserbyUdid(String udid);
	/**
	 * 根据用户Iden获取用户信息
	 * @param userIden 用户Iden
	 * @return 返回用户信息
	 */
	public User getUserbyUserIden(String userIden);
	/**
	 * 用户登录
	 * @param userName 用户名 
	 * @param pwd 密码
	 * @param ip 用户登录ip
	 * @return 返回用户登录枚举对象
	 */
	public UserLoginResult login(String userName,byte[] pwd,String ip);
	/**
	 * 冻结用户
	 * @param userIden
	 * @return
	 */
	public int freezeUser(String userIden);
	
	/**
	 * 用户注册
	 * @param userName 用户名
	 * @param pwd 密码
	 * @param udid 用户设备ID
	 * @param osType 客户端类型
	 * @param locale 客户端语言
	 * @param releaseChannel 发布平台
	 * @param registerType 注册类型 
	 * @return
	 */
	public UserRegisterResult register(String userName,byte[] pwd,String udid,String osType,String locale,String releaseChannel,int registerType);
	/**
	 * 绑定Email
	 * @param udid
	 * @param Email
	 * @return
	 */
	public int bindEmail(String userIden,String Email);
	/**
	 * 绑定激活
	 * @param Email
	 * @param udid
	 * @param code
	 * @return
	 */
	public ActiveResult activeUserEmail(String email,String udid,String code);
	/**
	 * 获取绑定Email的用户
	 * @param udid
	 * @param email
	 * @param code
	 * @return
	 */
	public ActiveEmail getActiveEmail(String udid,String email,String code);
	/**
	 * 获取绑定Email的用户
	 * @param udid
	 * @param email
	 * @return
	 */
	public ActiveEmail getActiveEmail(String udid,String email);
	/**
	 * 修改用户密码
	 * @param email
	 * @param pwd
	 * @return
	 */
	public int updateUserPwdByEmail(String email,String pwd);
	/**
	 * 修改用户密码
	 * @param userName
	 * @param pwd
	 * @return
	 */
	public int updateUserPwdByUserName(String userName,String pwd);
}
