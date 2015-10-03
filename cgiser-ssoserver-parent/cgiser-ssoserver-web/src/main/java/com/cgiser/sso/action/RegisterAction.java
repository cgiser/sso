package com.cgiser.sso.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgiser.keel.http.utils.HttpSpringUtils;
import com.cgiser.sso.common.utils.ServletUtil;
import com.cgiser.sso.manager.GameManager;
import com.cgiser.sso.manager.UserManager;
import com.cgiser.sso.model.Game;
import com.cgiser.sso.model.RegisterEnum;
import com.cgiser.sso.model.RegisterType;
import com.cgiser.sso.model.ReturnType;
import com.cgiser.sso.model.User;
import com.cgiser.sso.model.UserRegisterResult;

public class RegisterAction extends AbstractAction {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String userName = ServletUtil.getDefaultValue(request, "un", ""); 
		String pwd = ServletUtil.getDefaultValue(request, "pw", ""); 
	    String udid = ServletUtil.getDefaultValue(request, "udid", "");    
	    String osType = ServletUtil.getDefaultValue(request, "ostype", "");
	    String locale = ServletUtil.getDefaultValue(request, "locale", "");
	    String releaseChannel = ServletUtil.getDefaultValue(request, "releasechannel", "");
	    String gameName = ServletUtil.getDefaultValue(request, "game", "");
	    String type = ServletUtil.getDefaultValue(request, "registerType", "");
	    ReturnType<User> returnType = new ReturnType<User>();
	    UserRegisterResult result ;
	    try{
	    	returnType.setMsg("注册失败");
	    	returnType.setStatus(0);

		    if(StringUtils.isBlank(gameName)){
		    	 result = new UserRegisterResult(RegisterEnum.REGISTER_FAILURE, 0, null);
		    	 returnType.setValue(null);
		    	 super.printReturnType2Response(response, returnType);
		    	 return null;
		    }

		    if(StringUtils.isBlank(osType)){
		    	 result = new UserRegisterResult(RegisterEnum.REGISTER_FAILURE, 0, null);
		    	 returnType.setValue(null);
		    	 super.printReturnType2Response(response, returnType);
		    	 return null;
		    }
		    if(StringUtils.isBlank(locale)){
		    	 result = new UserRegisterResult(RegisterEnum.REGISTER_FAILURE, 0, null);
		    	 returnType.setValue(null);
		    	 super.printReturnType2Response(response, returnType);
		    	 return null;
		    }
		    if(StringUtils.isBlank(udid)){
		    	 result = new UserRegisterResult(RegisterEnum.REGISTER_FAILURE, 0, null);
		    	 returnType.setValue(null);
		    	 super.printReturnType2Response(response, returnType);
		    	 return null;
		    }
		    if(StringUtils.isBlank(releaseChannel)){
		    	 result = new UserRegisterResult(RegisterEnum.REGISTER_FAILURE, 0, null);
		    	 returnType.setValue(null);
		    	 super.printReturnType2Response(response, returnType);
		    	 return null;
		    }
		    GameManager gameManager = (GameManager)HttpSpringUtils.getBean("gameManager");
		    Game game = gameManager.getGameByGameName(gameName);
		    if(game==null){
		    	 result = new UserRegisterResult(RegisterEnum.REGISTER_FAILURE, 0, null);
		    	 returnType.setValue(null);
		    	 super.printReturnType2Response(response, returnType);
		    	 return null;
		    }
		    if(RegisterType.valueOf(Integer.parseInt(type))==RegisterType.udid){
		    	userName = RandomStringUtils.randomAlphabetic(6);
		    	pwd = RandomStringUtils.randomAlphanumeric(8);
		    }else if(RegisterType.valueOf(Integer.parseInt(type))==RegisterType.user){
			    if(StringUtils.isBlank(userName)){
			    	 result = new UserRegisterResult(RegisterEnum.REGISTER_NICK_INVALIDATE, 0, null);
			    	 returnType.setValue(null);
			    	 super.printReturnType2Response(response, returnType);
			    	 return null;
			    }
			    if(!com.cgiser.sso.common.utils.StringUtils.checkUserNameValid(userName)){
			    	 result = new UserRegisterResult(RegisterEnum.REGISTER_NICK_INVALIDATE, 0, null);
			    	 returnType.setValue(null);
			    	 returnType.setMsg("用户名由2-10个数字、26个英文字母或者下划线组成的字符串");
			    	 super.printReturnType2Response(response, returnType);
			    	 return null;
			    }
			    if(StringUtils.isBlank(pwd)){
			    	 result = new UserRegisterResult(RegisterEnum.REGISTER_PASSWORD_INVALIDATE, 0, null);
			    	 returnType.setValue(null);
			    	 super.printReturnType2Response(response, returnType);
			    	 return null;
			    }
			    if(!com.cgiser.sso.common.utils.StringUtils.checkPasswordValid(pwd)){
			    	 result = new UserRegisterResult(RegisterEnum.REGISTER_PASSWORD_INVALIDATE, 0, null);
			    	 returnType.setValue(null);
			    	 returnType.setMsg("密码由4-8个数字、26个英文字母或者下划线组成的字符串");
			    	 super.printReturnType2Response(response, returnType);
			    	 return null;
			    }
		    }
		    UserManager userManager = (UserManager)HttpSpringUtils.getBean("userManager");
//		    List<User> userList = userManager.getUserbyUdid(udid);
//		    if(!CollectionUtils.isEmpty(userList)){
//		    	 result = new UserRegisterResult(RegisterEnum.REGISTER_PASSWORD_INVALIDATE, 0, null);
//		    	 returnType.setValue(null);
//		    	 returnType.setMsg("NotEnoughAuthor");
//		    	 super.printReturnType2Response(response, returnType);
//		    	 return null;
//		    }
//		    if(userList.size()>=7){
//		    	 result = new UserRegisterResult(RegisterEnum.REGISTER_PASSWORD_INVALIDATE, 0, null);
//		    	 returnType.setValue(null);
//		    	 returnType.setMsg("NotEnoughAuthor");
//		    	 super.printReturnType2Response(response, returnType);
//		    	 return null;
//		    }
		    User user = null;
		    if(!StringUtils.isBlank(udid)){
		    	user = userManager.getUserbyUdid(udid);
		    }
		    result = userManager.register(userName, pwd.getBytes(), udid, osType, locale, releaseChannel,Integer.parseInt(type));
//		    DeviceGameManager deviceGameManager = (DeviceGameManager)HttpSpringUtils.getBean("deviceGameManager");
		    if(result.isSuccess()){
			    if(!StringUtils.isBlank(udid)){
			    	if(user!=null){
			    		if(userManager.freezeUser(user.getUserIden())<1){
			    			logger.error("冻结用户失败："+user.getUserName());
			    		}
			    	}
			    }
			    returnType.setMsg("注册成功");
		    	returnType.setStatus(1);
		    	returnType.setValue(result.getBusinessObject());
		    	super.printReturnType2Response(response, returnType);
//		    	DeviceGame deviceGame = deviceGameManager.getDeviceGameByUdidGameName(udid, gameName);
//		    	if(deviceGame==null){
//			    	if(deviceGameManager.activeDeviceUser(game.getGameName(),game.getGameIden(),result.getBusinessObject().getUserIden(),udid)){
//				    	returnType.setMsg("注册成功");
//				    	returnType.setStatus(1);
//				    	returnType.setValue(result);
//				    	super.printReturnType2Response(response, returnType);
//			    	}
//		    	}else{
//		    		if(deviceGameManager.updateDeviceUser(udid, gameName, result.getBusinessObject().getUserIden())>0){
//				    	returnType.setMsg("注册成功");
//				    	returnType.setStatus(1);
//				    	returnType.setValue(result);
//				    	super.printReturnType2Response(response, returnType);
//		    		}
//		    	}
//		    	if(device!=null){
//		    		if(userManager.freezeUser(device.getUserIden())<1){
//		    			logger.error("冻结用户失败："+device.getUserIden());
//		    		}
//		    	}
		    	return null;

		    }
	    	if(result.getBusinessResult()==RegisterEnum.REGISTER_NICK_EXIST){
	    		returnType.setMsg("用户存在，直接登录");
		    	returnType.setStatus(2);
		    	returnType.setValue(null);
		    	super.printReturnType2Response(response, returnType);
		    	return null;
	    	}
    		super.printReturnType2Response(response, returnType);
    		return null;
	    }catch (IOException e) {
			logger.error("注册失败"+e.getMessage());
		}
	    return null;

	}
}
