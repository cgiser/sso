package com.cgiser.sso.action;

import java.io.IOException;
import java.util.List;

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
import com.cgiser.keel.user.CasUserManager;
import com.cgiser.sso.common.utils.ServletUtil;
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

public class WebLoginAction extends AbstractAction {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String userName = null;
		String pwd = null;
		String uin = null;
		// String deviceType = ServletUtil.getDefaultValue(request,
		// "devicetype",
		// "4");
		String loginType = ServletUtil
				.getDefaultValue(request, "logintype", "");
		String udid = ServletUtil.getDefaultValue(request, "udid", "");
		String gameName = ServletUtil.getDefaultValue(request, "game", "");
		String osType = ServletUtil.getDefaultValue(request, "ostype", "");
		String locale = ServletUtil.getDefaultValue(request, "locale", "");
		String releaseChannel = ServletUtil.getDefaultValue(request,
				"releasechannel", "");

		UserManager userManager = (UserManager) HttpSpringUtils
				.getBean("userManager");
		CasUserManager casUserManager = CasUserManager.getUserManagerInstance();
		ReturnType<UserLoginResult> returnType = new ReturnType<UserLoginResult>();
		User user = casUserManager.getUser();
		if(user!=null){
			returnType.setStatus(1);
			GameManager gameManager = (GameManager) HttpSpringUtils
					.getBean("gameManager");
			List<GameServer> gameServerList = gameManager.getAllGameServerList();
			UserLoginResult reslut = new UserLoginResult(
					LoginEnum.LOGON_SUCCESS, 1, new UserGameDevice());
			
			request.setAttribute("user", user);
			Game game = new Game();
			game.setGameServerList(gameServerList);
			reslut.getBusinessObject().setUser(user);
			reslut.getBusinessObject().setGame(game);
			request.setAttribute("game", game);
			return mapping.findForward("user");
		}
		if (LoginType.valueFromDesc(loginType) == LoginType.USERPWD) {
			try {
				userName = ServletUtil.getDefaultValue(request, "un", "");
				pwd = ServletUtil.getDefaultValue(request, "pw", "");
				if (StringUtils.isBlank(gameName)) {
					UserLoginResult result = new UserLoginResult(
							LoginEnum.LOGON_FAILURE, 0, null);
					returnType.setValue(result);
					super.printReturnType2Response(response, returnType);
					return null;
				}
				UserLoginResult reslut = casUserManager.login(userName, pwd.getBytes(), ServletUtil.getUserIP(request));
				logger.info("用户登录结果" + JSONObject.fromObject(reslut));
				returnType.setValue(reslut);
				if (reslut.isSuccess()) {
					logger.info("用户登录成功" + JSONObject.fromObject(reslut));
					returnType.setStatus(1);
					request.setAttribute("user", reslut.getBusinessObject()
							.getUser());
					GameManager gameManager = (GameManager) HttpSpringUtils
					.getBean("gameManager");
					List<GameServer> gameServerList = gameManager.getAllGameServerList();
					Game game = new Game();
					game.setGameServerList(gameServerList);
					request.setAttribute("game", game);
					return mapping.findForward("user");
				} else {
					return mapping.findForward("login");
				}
			} catch (IOException e) {
				return mapping.findForward("login");
			}
		} if (LoginType.valueFromDesc(loginType) == LoginType.QQLogin) {
			uin = ServletUtil.getDefaultValue(request, "uin", "");
			try {
				if (StringUtils.isBlank(uin)) {
					UserLoginResult result = new UserLoginResult(
							LoginEnum.LOGON_FAILURE, 0, null);
					returnType.setValue(result);
					super.printReturnType2Response(response, returnType);
					return null;
				}
				// NineOneSdk nineOneSdk =
				// (NineOneSdk)HttpSpringUtils.getBean("nineOneSdk");
				// int a = nineOneSdk.checkUserLogin(uin, sessionID);
				// if(a!=1){
				// UserLoginResult result = new
				// UserLoginResult(LoginEnum.LOGON_FAILURE, 0, null);
				// returnType.setValue(result);
				// super.printReturnType2Response(response, returnType);
				// return null;
				// }
				user = userManager.getUserbyUserName(uin + "@qq.com");
				if (user == null) {
					UserRegisterResult rResult = new UserRegisterResult(
							RegisterEnum.REGISTER_FAILURE, 0, null);
					pwd = RandomStringUtils.randomAlphanumeric(8);
					rResult = userManager.register(uin + "@qq.com", pwd
							.getBytes(), udid, osType, locale, releaseChannel,
							RegisterType.QQLogin.getValue());
					if (rResult.isFailure()) {
						UserLoginResult result = new UserLoginResult(
								LoginEnum.LOGON_FAILURE, 0, null);
						returnType.setValue(result);
						super.printReturnType2Response(response, returnType);
						return null;
					}
					user = userManager.getUserbyUserName(uin + "@qq.com");
				}
				returnType.setStatus(1);
				GameManager gameManager = (GameManager) HttpSpringUtils
						.getBean("gameManager");
				Game game = gameManager.getGameByGameName(gameName);
				UserLoginResult reslut = new UserLoginResult(
						LoginEnum.LOGON_SUCCESS, 1, new UserGameDevice());
				reslut.getBusinessObject().setGame(game);
				reslut.getBusinessObject().setUser(user);
				DeviceGameManager deviceGameManager = (DeviceGameManager) HttpSpringUtils
						.getBean("deviceGameManager");
				for (GameServer server : game.getGameServerList()) {
					DeviceGame deviceGame = deviceGameManager.getUserGame(game
							.getGameIden(), user.getUserIden(), server
							.getServerId());
					if (deviceGame != null) {
						server.setRoleNum(deviceGame.getRoleNum());
					} else {
						server.setRoleNum(0);
					}

				}
				request.setAttribute("user", user);
				request.setAttribute("game", game);
				return mapping.findForward("user");
			} catch (IOException e) {
				return mapping.findForward("login");
			} catch (Exception e) {
				return mapping.findForward("login");
			}
		} else {
			logger.info("用户登录失败：不支持此登录类型");
			returnType.setStatus(0);
			returnType.setValue(new UserLoginResult(LoginEnum.LOGON_FAILURE,
					-2, null));
			return mapping.findForward("login");
		}


	}
}
