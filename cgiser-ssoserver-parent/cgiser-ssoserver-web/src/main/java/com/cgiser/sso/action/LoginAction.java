package com.cgiser.sso.action;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgiser.keel.http.utils.HttpSpringUtils;
import com.cgiser.sso.common.utils.ServletUtil;
import com.cgiser.sso.dao.util.DigestUtils;
import com.cgiser.sso.manager.DeviceGameManager;
import com.cgiser.sso.manager.GameManager;
import com.cgiser.sso.manager.UserManager;
import com.cgiser.sso.model.DeviceGame;
import com.cgiser.sso.model.Game;
import com.cgiser.sso.model.GameServer;
import com.cgiser.sso.model.LoginEnum;
import com.cgiser.sso.model.LoginType;
import com.cgiser.sso.model.RegisterEnum;
import com.cgiser.sso.model.RegisterType;
import com.cgiser.sso.model.ReturnType;
import com.cgiser.sso.model.User;
import com.cgiser.sso.model.UserGameDevice;
import com.cgiser.sso.model.UserLoginResult;
import com.cgiser.sso.model.UserRegisterResult;
import com.cgiser.sso.support.BaiDuSdk;
import com.cgiser.sso.support.NineOneSdk;
import com.cgiser.sso.support.WdjSdk;

public class LoginAction extends AbstractAction {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String userName = null;
		String pwd = null;
		String uin = null;
		String sessionID = null;
//		String deviceType = ServletUtil.getDefaultValue(request, "devicetype",
//				"4");
		String loginType = ServletUtil
				.getDefaultValue(request, "logintype", "");
		String udid = ServletUtil.getDefaultValue(request, "udid", "");
		String gameName = ServletUtil.getDefaultValue(request, "game", "");
		String osType = ServletUtil.getDefaultValue(request, "ostype", "");
		String locale = ServletUtil.getDefaultValue(request, "locale", "");
		String releaseChannel = ServletUtil.getDefaultValue(request, "releasechannel", "");
		
		UserManager userManager = (UserManager) HttpSpringUtils
				.getBean("userManager");
		ReturnType<UserLoginResult> returnType = new ReturnType<UserLoginResult>();
		if (LoginType.valueFromDesc(loginType) == LoginType.DEVICE) {
			try {
				if (StringUtils.isBlank(udid)) {
					logger.info("用户登录失败：设备ID为空");
					returnType.setValue(new UserLoginResult(LoginEnum.FAKE_LOGON_ID_EN_EMPTY, -2, null));
					super.printReturnType2Response(response, returnType);
					return null;
				}
				User user = userManager.getUserbyUdid(udid);
				if (user == null) {
					// 用户不存在，是设备登录证明是直接开始游戏，注册用户
					logger.info("用户登录失败：设备ID为空");
					super.printReturnType2Response(response, returnType);
					return null;
				}
				returnType.setStatus(1);
				GameManager gameManager = (GameManager)HttpSpringUtils.getBean("gameManager");
				Game game = gameManager.getGameByGameName(gameName);
				UserLoginResult reslut = new UserLoginResult(LoginEnum.LOGON_SUCCESS, 1, new UserGameDevice());
				reslut.getBusinessObject().setGame(game);
				reslut.getBusinessObject().setUser(user);
				DeviceGameManager deviceGameManager = (DeviceGameManager)HttpSpringUtils.getBean("deviceGameManager");
				for(GameServer server:game.getGameServerList()){
					DeviceGame deviceGame = deviceGameManager.getUserGame(game.getGameIden(), user.getUserIden(), server.getServerId());
					if(deviceGame!=null){
						server.setRoleNum(deviceGame.getRoleNum());
					}else{
						server.setRoleNum(0);
					}
					
				}
				
				returnType.setValue(reslut);
				super.printReturnType2Response(response, returnType);
				return null;
			} catch (Exception e) {
				// TODO: handle exception
			}

		} else if(LoginType.valueFromDesc(loginType)==LoginType.USERPWD) {
			try {
				userName = ServletUtil.getDefaultValue(request, "un", "");
				pwd = ServletUtil.getDefaultValue(request, "pw", "");
			    if(StringUtils.isBlank(gameName)){
			    	UserLoginResult result = new UserLoginResult(LoginEnum.LOGON_FAILURE, 0, null);
			    	returnType.setValue(result);
			    	super.printReturnType2Response(response, returnType);
			    	return null;
			    }
				UserLoginResult reslut = userManager.login(userName, pwd
						.getBytes(), ServletUtil.getUserIP(request));
				logger.info("用户登录结果" + JSONObject.fromObject(reslut));
				returnType.setValue(reslut);
				if (reslut.isSuccess()) {
					logger.info("用户登录成功" + JSONObject.fromObject(reslut));
					returnType.setStatus(1);
					GameManager gameManager = (GameManager)HttpSpringUtils.getBean("gameManager");
					Game game = gameManager.getGameByGameName(gameName);
					reslut.getBusinessObject().setGame(game);
					DeviceGameManager deviceGameManager = (DeviceGameManager)HttpSpringUtils.getBean("deviceGameManager");
					for(GameServer server:game.getGameServerList()){
						DeviceGame deviceGame = deviceGameManager.getUserGame(game.getGameIden(), reslut.getBusinessObject().getUser().getUserIden(), server.getServerId());
						if(deviceGame!=null){
							server.setRoleNum(deviceGame.getRoleNum());
						}else{
							server.setRoleNum(0);
						}
						
					}
					response.addCookie(new Cookie("_jid", DigestUtils.digest(userName)));
					super.printReturnType2Response(response, returnType);
					return null;
				} else {
					logger.info("用户登录失败" + JSONObject.fromObject(reslut));
					super.printReturnType2Response(response, returnType);
					return null;
				}
			} catch (IOException e) {
				logger.error("用户登录异常" + e.getMessage());
				// e.printStackTrace();
			}
		}else if(LoginType.valueFromDesc(loginType)==LoginType.NineOneLogin){
			uin = ServletUtil.getDefaultValue(request, "uin", "");
			sessionID = ServletUtil.getDefaultValue(request, "sessionId", "");
			try {
				if(StringUtils.isBlank(uin)||StringUtils.isBlank(sessionID)){
					UserLoginResult result = new UserLoginResult(LoginEnum.LOGON_FAILURE, 0, null);
			    	returnType.setValue(result);
			    	super.printReturnType2Response(response, returnType);
			    	return null;
				}
				NineOneSdk nineOneSdk = (NineOneSdk)HttpSpringUtils.getBean("nineOneSdk");
				int a = nineOneSdk.checkUserLogin(uin, sessionID);
				if(a!=1){
					UserLoginResult result = new UserLoginResult(LoginEnum.LOGON_FAILURE, 0, null);
			    	returnType.setValue(result);
			    	super.printReturnType2Response(response, returnType);
			    	return null;
				}
				User user = userManager.getUserbyUserName(uin);
				if(user==null){
					UserRegisterResult rResult = new UserRegisterResult(RegisterEnum.REGISTER_FAILURE, 0, null);
			    	pwd = RandomStringUtils.randomAlphanumeric(8);
					rResult = userManager.register(uin, pwd.getBytes(), udid, osType, locale, releaseChannel, RegisterType.NineOneLogin.getValue());
					if(rResult.isFailure()){
						UserLoginResult result = new UserLoginResult(LoginEnum.LOGON_FAILURE, 0, null);
				    	returnType.setValue(result);
				    	super.printReturnType2Response(response, returnType);
				    	return null;
					}
					user = userManager.getUserbyUserName(uin);
				}
				returnType.setStatus(1);
				GameManager gameManager = (GameManager)HttpSpringUtils.getBean("gameManager");
				Game game = gameManager.getGameByGameName(gameName);
				UserLoginResult reslut = new UserLoginResult(LoginEnum.LOGON_SUCCESS, 1, new UserGameDevice());
				reslut.getBusinessObject().setGame(game);
				reslut.getBusinessObject().setUser(user);
				DeviceGameManager deviceGameManager = (DeviceGameManager)HttpSpringUtils.getBean("deviceGameManager");
				for(GameServer server:game.getGameServerList()){
					DeviceGame deviceGame = deviceGameManager.getUserGame(game.getGameIden(), user.getUserIden(), server.getServerId());
					if(deviceGame!=null){
						server.setRoleNum(deviceGame.getRoleNum());
					}else{
						server.setRoleNum(0);
					}
					
				}
				
				returnType.setValue(reslut);
				super.printReturnType2Response(response, returnType);
				return null;
			}catch (IOException e) {
				logger.error("用户登录异常" + e.getMessage());
				// e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(LoginType.valueFromDesc(loginType)==LoginType.QQLogin){
			uin = ServletUtil.getDefaultValue(request, "uin", "");
			try {
				if(StringUtils.isBlank(uin)){
					UserLoginResult result = new UserLoginResult(LoginEnum.LOGON_FAILURE, 0, null);
			    	returnType.setValue(result);
			    	super.printReturnType2Response(response, returnType);
			    	return null;
				}
//				NineOneSdk nineOneSdk = (NineOneSdk)HttpSpringUtils.getBean("nineOneSdk");
//				int a = nineOneSdk.checkUserLogin(uin, sessionID);
//				if(a!=1){
//					UserLoginResult result = new UserLoginResult(LoginEnum.LOGON_FAILURE, 0, null);
//			    	returnType.setValue(result);
//			    	super.printReturnType2Response(response, returnType);
//			    	return null;
//				}
				User user = userManager.getUserbyUserName(uin+"@qq.com");
				if(user==null){
					UserRegisterResult rResult = new UserRegisterResult(RegisterEnum.REGISTER_FAILURE, 0, null);
			    	pwd = RandomStringUtils.randomAlphanumeric(8);
					rResult = userManager.register(uin+"@qq.com", pwd.getBytes(), udid, osType, locale, releaseChannel, RegisterType.QQLogin.getValue());
					if(rResult.isFailure()){
						UserLoginResult result = new UserLoginResult(LoginEnum.LOGON_FAILURE, 0, null);
				    	returnType.setValue(result);
				    	super.printReturnType2Response(response, returnType);
				    	return null;
					}
					user = userManager.getUserbyUserName(uin+"@qq.com");
				}
				returnType.setStatus(1);
				GameManager gameManager = (GameManager)HttpSpringUtils.getBean("gameManager");
				Game game = gameManager.getGameByGameName(gameName);
				UserLoginResult reslut = new UserLoginResult(LoginEnum.LOGON_SUCCESS, 1, new UserGameDevice());
				reslut.getBusinessObject().setGame(game);
				reslut.getBusinessObject().setUser(user);
				DeviceGameManager deviceGameManager = (DeviceGameManager)HttpSpringUtils.getBean("deviceGameManager");
				for(GameServer server:game.getGameServerList()){
					DeviceGame deviceGame = deviceGameManager.getUserGame(game.getGameIden(), user.getUserIden(), server.getServerId());
					if(deviceGame!=null){
						server.setRoleNum(deviceGame.getRoleNum());
					}else{
						server.setRoleNum(0);
					}
					
				}
				
				returnType.setValue(reslut);
				super.printReturnType2Response(response, returnType);
				return null;
			}catch (IOException e) {
				logger.error("用户登录异常" + e.getMessage());
				// e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(LoginType.valueFromDesc(loginType)==LoginType.WDJLogin){
			uin = ServletUtil.getDefaultValue(request, "uin", "");
			String token = ServletUtil.getDefaultValue(request, "token", "");
			try {
				if(StringUtils.isBlank(uin)){
					UserLoginResult result = new UserLoginResult(LoginEnum.LOGON_FAILURE, 0, null);
			    	returnType.setValue(result);
			    	super.printReturnType2Response(response, returnType);
			    	return null;
				}
//				NineOneSdk nineOneSdk = (NineOneSdk)HttpSpringUtils.getBean("nineOneSdk");
//				int a = nineOneSdk.checkUserLogin(uin, sessionID);
//				if(a!=1){
//					UserLoginResult result = new UserLoginResult(LoginEnum.LOGON_FAILURE, 0, null);
//			    	returnType.setValue(result);
//			    	super.printReturnType2Response(response, returnType);
//			    	return null;
//				}
				WdjSdk wdjSdk = (WdjSdk)HttpSpringUtils.getBean("wdjSdk");
				int a = wdjSdk.checkLogin(uin, token);
				if(a!=1){
					UserLoginResult result = new UserLoginResult(LoginEnum.LOGON_FAILURE, 0, null);
			    	returnType.setValue(result);
			    	super.printReturnType2Response(response, returnType);
			    	return null;
				}
				User user = userManager.getUserbyUserName(uin+"@wdj.com");
				if(user==null){
					UserRegisterResult rResult = new UserRegisterResult(RegisterEnum.REGISTER_FAILURE, 0, null);
			    	pwd = RandomStringUtils.randomAlphanumeric(8);
					rResult = userManager.register(uin+"@wdj.com", pwd.getBytes(), udid, osType, locale, releaseChannel, RegisterType.WDJLogin.getValue());
					if(rResult.isFailure()){
						UserLoginResult result = new UserLoginResult(LoginEnum.LOGON_FAILURE, 0, null);
				    	returnType.setValue(result);
				    	super.printReturnType2Response(response, returnType);
				    	return null;
					}
					user = userManager.getUserbyUserName(uin+"@wdj.com");
				}
				returnType.setStatus(1);
				GameManager gameManager = (GameManager)HttpSpringUtils.getBean("gameManager");
				Game game = gameManager.getGameByGameName(gameName);
				UserLoginResult reslut = new UserLoginResult(LoginEnum.LOGON_SUCCESS, 1, new UserGameDevice());
				reslut.getBusinessObject().setGame(game);
				reslut.getBusinessObject().setUser(user);
				DeviceGameManager deviceGameManager = (DeviceGameManager)HttpSpringUtils.getBean("deviceGameManager");
				for(GameServer server:game.getGameServerList()){
					DeviceGame deviceGame = deviceGameManager.getUserGame(game.getGameIden(), user.getUserIden(), server.getServerId());
					if(deviceGame!=null){
						server.setRoleNum(deviceGame.getRoleNum());
					}else{
						server.setRoleNum(0);
					}
					
				}
				
				returnType.setValue(reslut);
				super.printReturnType2Response(response, returnType);
				return null;
			}catch (IOException e) {
				logger.error("用户登录异常" + e.getMessage());
				// e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(LoginType.valueFromDesc(loginType)==LoginType.BaiDuLogin){
			uin = ServletUtil.getDefaultValue(request, "uin", "");
			String token = ServletUtil.getDefaultValue(request, "token", "");
			try {
				if(StringUtils.isBlank(uin)){
					UserLoginResult result = new UserLoginResult(LoginEnum.LOGON_FAILURE, 0, null);
			    	returnType.setValue(result);
			    	super.printReturnType2Response(response, returnType);
			    	return null;
				}
				
				JSONObject obj = JSONObject.fromObject(BaiDuSdk.getUserInfo("2234101", token));
				if(obj.get("result").equals("0")){
					UserLoginResult result = new UserLoginResult(LoginEnum.LOGON_FAILURE, 0, null);
			    	returnType.setValue(result);
			    	super.printReturnType2Response(response, returnType);
			    	return null;
				}
				JSONObject userObj = JSONObject.fromObject(obj.get("value"));
				if(!userObj.get("userid").equals(uin)){
					UserLoginResult result = new UserLoginResult(LoginEnum.LOGON_FAILURE, 0, null);
			    	returnType.setValue(result);
			    	super.printReturnType2Response(response, returnType);
			    	return null;
				}
				User user = userManager.getUserbyUserName(uin+"@baidu.com");
				if(user==null){
					UserRegisterResult rResult = new UserRegisterResult(RegisterEnum.REGISTER_FAILURE, 0, null);
			    	pwd = RandomStringUtils.randomAlphanumeric(8);
					rResult = userManager.register(uin+"@baidu.com", pwd.getBytes(), udid, osType, locale, releaseChannel, RegisterType.BaiDuLogin.getValue());
					if(rResult.isFailure()){
						UserLoginResult result = new UserLoginResult(LoginEnum.LOGON_FAILURE, 0, null);
				    	returnType.setValue(result);
				    	super.printReturnType2Response(response, returnType);
				    	return null;
					}
					user = userManager.getUserbyUserName(uin+"@baidu.com");
				}
				returnType.setStatus(1);
				GameManager gameManager = (GameManager)HttpSpringUtils.getBean("gameManager");
				Game game = gameManager.getGameByGameName(gameName);
				UserLoginResult reslut = new UserLoginResult(LoginEnum.LOGON_SUCCESS, 1, new UserGameDevice());
				reslut.getBusinessObject().setGame(game);
				reslut.getBusinessObject().setUser(user);
				DeviceGameManager deviceGameManager = (DeviceGameManager)HttpSpringUtils.getBean("deviceGameManager");
				for(GameServer server:game.getGameServerList()){
					DeviceGame deviceGame = deviceGameManager.getUserGame(game.getGameIden(), user.getUserIden(), server.getServerId());
					if(deviceGame!=null){
						server.setRoleNum(deviceGame.getRoleNum());
					}else{
						server.setRoleNum(0);
					}
					
				}
				
				returnType.setValue(reslut);
				super.printReturnType2Response(response, returnType);
				return null;
			}catch (IOException e) {
				logger.error("用户登录异常" + e.getMessage());
				// e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(LoginType.valueFromDesc(loginType)==LoginType.MMLogin){
			uin = ServletUtil.getDefaultValue(request, "uin", "");
//			String token = ServletUtil.getDefaultValue(request, "token", "");
			try {
				if(StringUtils.isBlank(uin)){
					UserLoginResult result = new UserLoginResult(LoginEnum.LOGON_FAILURE, 0, null);
			    	returnType.setValue(result);
			    	super.printReturnType2Response(response, returnType);
			    	return null;
				}
				
//				JSONObject obj = JSONObject.fromObject(BaiDuSdk.getUserInfo("2234101", token));
//				if(obj.get("result").equals("0")){
//					UserLoginResult result = new UserLoginResult(LoginEnum.LOGON_FAILURE, 0, null);
//			    	returnType.setValue(result);
//			    	super.printReturnType2Response(response, returnType);
//			    	return null;
//				}
//				JSONObject userObj = JSONObject.fromObject(obj.get("value"));
//				if(!userObj.get("userid").equals(uin)){
//					UserLoginResult result = new UserLoginResult(LoginEnum.LOGON_FAILURE, 0, null);
//			    	returnType.setValue(result);
//			    	super.printReturnType2Response(response, returnType);
//			    	return null;
//				}
				User user = userManager.getUserbyUserName(uin+"@mm.com");
				if(user==null){
					UserRegisterResult rResult = new UserRegisterResult(RegisterEnum.REGISTER_FAILURE, 0, null);
			    	pwd = RandomStringUtils.randomAlphanumeric(8);
					rResult = userManager.register(uin+"@mm.com", pwd.getBytes(), udid, osType, locale, releaseChannel, RegisterType.MMLogin.getValue());
					if(rResult.isFailure()){
						UserLoginResult result = new UserLoginResult(LoginEnum.LOGON_FAILURE, 0, null);
				    	returnType.setValue(result);
				    	super.printReturnType2Response(response, returnType);
				    	return null;
					}
					user = userManager.getUserbyUserName(uin+"@mm.com");
				}
				returnType.setStatus(1);
				GameManager gameManager = (GameManager)HttpSpringUtils.getBean("gameManager");
				Game game = gameManager.getGameByGameName(gameName);
				UserLoginResult reslut = new UserLoginResult(LoginEnum.LOGON_SUCCESS, 1, new UserGameDevice());
				reslut.getBusinessObject().setGame(game);
				reslut.getBusinessObject().setUser(user);
				DeviceGameManager deviceGameManager = (DeviceGameManager)HttpSpringUtils.getBean("deviceGameManager");
				for(GameServer server:game.getGameServerList()){
					DeviceGame deviceGame = deviceGameManager.getUserGame(game.getGameIden(), user.getUserIden(), server.getServerId());
					if(deviceGame!=null){
						server.setRoleNum(deviceGame.getRoleNum());
					}else{
						server.setRoleNum(0);
					}
					
				}
				
				returnType.setValue(reslut);
				super.printReturnType2Response(response, returnType);
				return null;
			}catch (IOException e) {
				logger.error("用户登录异常" + e.getMessage());
				// e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			logger.info("用户登录失败：不支持此登录类型");
			returnType.setStatus(0);
			returnType.setValue(new UserLoginResult(LoginEnum.LOGON_FAILURE, -2, null));
			try {
				super.printReturnType2Response(response, returnType);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		return null;

	}
}
