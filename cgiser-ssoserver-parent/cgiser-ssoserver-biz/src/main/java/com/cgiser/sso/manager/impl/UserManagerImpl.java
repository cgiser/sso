package com.cgiser.sso.manager.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.util.CollectionUtils;

import com.cgiser.sso.common.utils.StringUtils;
import com.cgiser.sso.dao.ActiveDao;
import com.cgiser.sso.dao.UserDao;
import com.cgiser.sso.dao.util.DigestUtils;
import com.cgiser.sso.dao.util.Passport;
import com.cgiser.sso.manager.DeviceGameManager;
import com.cgiser.sso.manager.DeviceManager;
import com.cgiser.sso.manager.EmailManager;
import com.cgiser.sso.manager.GameManager;
import com.cgiser.sso.manager.UserManager;
import com.cgiser.sso.model.ActiveEmail;
import com.cgiser.sso.model.ActiveEnum;
import com.cgiser.sso.model.ActiveResult;
import com.cgiser.sso.model.LoginEnum;
import com.cgiser.sso.model.RegisterEnum;
import com.cgiser.sso.model.RegisterType;
import com.cgiser.sso.model.User;
import com.cgiser.sso.model.UserGameDevice;
import com.cgiser.sso.model.UserLoginResult;
import com.cgiser.sso.model.UserRegisterResult;

public class UserManagerImpl implements UserManager {
	private DeviceManager deviceManager;
	private DeviceGameManager deviceGameManager;
	private GameManager gameManager;
	private UserDao userDao;
	private ActiveDao activeDao;
	private EmailManager emailManager;
	@Override
	public User getUserbyUserName(String userName) {
		Map<String, Object> map = userDao.getUserByUserName(userName);
		return MapToUser(map);
	}

	@Override
	public UserLoginResult login(String userName, byte[] pwd, String ip) {
		User user;
		if(StringUtils.checkUserEmailValid(userName)){
			user = this.getUserbyEmail(userName);
		}else{
			user = this.getUserbyUserName(userName);
		}
		
		UserLoginResult result;
		if(user==null){
			result = new UserLoginResult(LoginEnum.LOGON_USER_NOT_EXIST, 0, null);
			return result;
		}
		if(DigestUtils.digest(new String(pwd)).toUpperCase().equals(user.getPwd())){
			UserGameDevice userGameDevice = new UserGameDevice();
			userGameDevice.setUser(user);
			userGameDevice.setDevice(deviceManager.getDeviceById(user.getUdid()));
			result = new UserLoginResult(LoginEnum.LOGON_SUCCESS, 1, userGameDevice);
			fakeUserSession(user.getUserIden());
		}else{
			result = new UserLoginResult(LoginEnum.LOGON_PASSWORD_WRONG, 0, null);
		}
		return result;
	}
    private void fakeUserSession(String userIden) {
//        if (org.apache.commons.lang.StringUtils.isBlank(userIden)) {
//            return;
//        }
//        HttpSession httpSession = WebProductContextHolder.getHttpSession();
//        httpSession.setAttribute("USERIDEN", userIden);
    }
	@Override
	public User getUserbyUserIden(String userIden) {
		Map<String, Object> map = userDao.getUserByUserIden(userIden);
		
		return MapToUser(map);
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public UserRegisterResult register(String userName, byte[] pwd,
			String udid, String osType, String locale, String releaseChannel,int registerType) {
		User user = this.getUserbyUserName(userName);
		UserRegisterResult result;
		if(user!=null){
			result = new UserRegisterResult(RegisterEnum.REGISTER_NICK_EXIST,0,null);
			return result;
		}
		String userId = String.valueOf(userDao.getSequence("t_cgiser_user"));
		String userIden = Passport.passport_encrypt(userId);
		Long id = userDao.saveUser(userId,userIden,userName, pwd, udid, osType, locale, releaseChannel,registerType);
		if(id>0){
//			user = this.getUserbyUserIden(userIden);
//			Device device = deviceManager.getDeviceById(udid);
//			if(device==null){
//				deviceManager.saveDevice(udid, userIden, osType);
//			}else{
//				deviceManager.updateDeviceUserIden(udid, userIden);
//			}
			user = this.getUserbyUdid(udid);
			result = new UserRegisterResult(RegisterEnum.REGISTER_SUCCESS,1,user);
			
		}else{
			result = new UserRegisterResult(RegisterEnum.REGISTER_FAILURE,0,null);
		}
		return result;
	}
	protected List<User> MapListToUserList(List<Map<String, Object>> list) {
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		List<User> userList = new ArrayList<User>();
		for(Map map:list){
			userList.add(MapToUser(map));
		}
		return userList;
	}
	protected User MapToUser(Map<String,Object> map){
		if(CollectionUtils.isEmpty(map)){
			return null;
		}
		User user = new User();
		user.setUserId(new Long(map.get("USERID").toString()));
		user.setUserIden((String)map.get("USERIDEN"));
		user.setUserName((String)map.get("USERNAME"));
		user.setCreateTime((Timestamp)map.get("CREATETIME"));
		user.setUdid((String)map.get("UDID"));
		user.setIsActive(Integer.parseInt(map.get("ISACTIVE").toString()));
		user.setState(Integer.parseInt(map.get("STATE").toString()));
		user.setPwd(map.get("USERPWD")==null?null:map.get("USERPWD").toString());
		user.setEmail(map.get("EMAIL")==null?null:map.get("EMAIL").toString());
		user.setRegisterType(Integer.parseInt(map.get("REGISTERTYPE").toString()));
		return user;
		
	}
	private ActiveEmail MapToActiveEmail(Map<String,Object> map){
		if(CollectionUtils.isEmpty(map)){
			return null;
		}
		ActiveEmail activeEmail = new ActiveEmail();
		activeEmail.setActiveCode(map.get("ACTIVECODE").toString());
		activeEmail.setCreateTime((Timestamp)map.get("CREATETIME"));
		activeEmail.setEmail(map.get("EMAIL").toString());
		activeEmail.setId(new Long(map.get("ID").toString()));
		activeEmail.setState(Integer.parseInt(map.get("STATE").toString()));
		activeEmail.setUserIden(map.get("USERIDEN").toString());
		return activeEmail;
	}
	public DeviceManager getDeviceManager() {
		return deviceManager;
	}

	public void setDeviceManager(DeviceManager deviceManager) {
		this.deviceManager = deviceManager;
	}

	@Override
	public int freezeUser(String userIden) {
		// TODO Auto-generated method stub
		return userDao.freezeUser(userIden);
	}

	public GameManager getGameManager() {
		return gameManager;
	}

	public void setGameManager(GameManager gameManager) {
		this.gameManager = gameManager;
	}

	public DeviceGameManager getDeviceGameManager() {
		return deviceGameManager;
	}

	public void setDeviceGameManager(DeviceGameManager deviceGameManager) {
		this.deviceGameManager = deviceGameManager;
	}

	@Override
	public int bindEmail(String userIden, String email) {
		String code = RandomStringUtils.randomNumeric(6);
		ActiveEmail activeEmail = this.getActiveEmail(userIden, email);
		if(activeEmail!=null&&activeEmail.getState()==2){
			Calendar curDate = Calendar.getInstance();
			curDate.add(Calendar.DATE, -2);
			if(curDate.getTime().before(activeEmail.getCreateTime())){
//				emailManager.sendMail(email, "http://192.168.2.12:8080/activeLogonId.do?logonId="+email+"&idType=Email&activeCode="+activeEmail.getActiveCode()+"&udid="+udid,"月光宝盒绑定邮箱激活邮件");
				emailManager.sendMail(email, "您的验证码为:"+activeEmail.getActiveCode(),"月光宝盒绑定邮箱验证码");
				return 1;
			}else{
				activeDao.updateActiveEmailState(userIden, email, activeEmail.getActiveCode(), 0);
			}
		}
		if(activeDao.addActive(userIden, email, code)>0){
//			emailManager.sendMail(email, "http://192.168.2.12:8080/activeLogonId.do?logonId="+email+"&idType=Email&activeCode="+code+"&udid="+udid,"月光宝盒绑定邮箱激活邮件");
			activeEmail = this.getActiveEmail(userIden, email);
			emailManager.sendMail(email, "您的验证码为:"+activeEmail.getActiveCode(),"月光宝盒绑定邮箱验证码");
			return 1;
		}
		return 0;
	}

	public ActiveDao getActiveDao() {
		return activeDao;
	}

	public void setActiveDao(ActiveDao activeDao) {
		this.activeDao = activeDao;
	}

	public EmailManager getEmailManager() {
		return emailManager;
	}

	public void setEmailManager(EmailManager emailManager) {
		this.emailManager = emailManager;
	}

	@Override
	public ActiveResult activeUserEmail(String email, String userIden, String code) {
		ActiveEmail activeEmail = this.getActiveEmail(userIden, email, code);
		if(activeEmail==null){
			return new ActiveResult(ActiveEnum.ACTIVE_FAILURE, 0, null);
		}
		activeDao.updateActiveEmailState(userIden, email, code,1);
		String pwd = RandomStringUtils.randomAlphanumeric(8);
//		User user = this.getUserbyUdid(udid);
		
		User user = this.getUserbyUserIden(userIden);
		if(user.getRegisterType()==RegisterType.udid.getValue()&&user.getEmail()==null){
			if(userDao.updateUserEmailPwd(userIden, email,DigestUtils.digest(pwd).toUpperCase())>0){
				user = this.getUserbyUserIden(userIden);
				emailManager.sendActiveSucessEmailPwd(email, user, pwd);
				return new ActiveResult(ActiveEnum.ACTIVE_SUCCESS, 0, user); 
			}
		}else{
			if(userDao.updateUserEmail(userIden, email)>0){
				user = this.getUserbyUserIden(userIden);
				emailManager.sendActiveSucessEmail(email, user);
				return new ActiveResult(ActiveEnum.ACTIVE_SUCCESS, 0, user); 
			}
		}
		
		
		return new ActiveResult(ActiveEnum.ACTIVE_FAILURE, 0, null);
	}

	@Override
	public ActiveEmail getActiveEmail(String userIden, String email, String code) {
		return MapToActiveEmail(activeDao.getActiveEmail(userIden, email, code));
	}

	@Override
	public User getUserbyEmail(String email) {
		// TODO Auto-generated method stub
		return MapToUser(userDao.getUserByEmail(email));
	}

	@Override
	public User getUserbyUdid(String udid) {
		// TODO Auto-generated method stub
		return MapToUser(userDao.getUserByUdid(udid));
	}

	@Override
	public ActiveEmail getActiveEmail(String userIden, String email) {
		// TODO Auto-generated method stub
		return MapToActiveEmail(activeDao.getActiveEmail(userIden, email));
	}

	@Override
	public int updateUserPwdByEmail(String email, String pwd) {
		if(userDao.updateUserPwdByEmail(email, DigestUtils.digest(pwd).toUpperCase())>0){
			User user = this.getUserbyEmail(email);
			emailManager.sendGetPasswordEmail(email, user, pwd);
			return 1;
		}
		return 0;
	}

	@Override
	public int updateUserPwdByUserName(String userName, String pwd) {
		// TODO Auto-generated method stub
		return userDao.updateUserPwdByUserName(userName, DigestUtils.digest(pwd).toUpperCase());
	}

}
