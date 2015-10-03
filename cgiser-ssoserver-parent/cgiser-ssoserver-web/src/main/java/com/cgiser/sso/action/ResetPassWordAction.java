package com.cgiser.sso.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgiser.keel.http.utils.HttpSpringUtils;
import com.cgiser.sso.common.utils.ServletUtil;
import com.cgiser.sso.common.utils.StringUtils;
import com.cgiser.sso.dao.util.DigestUtils;
import com.cgiser.sso.manager.UserManager;
import com.cgiser.sso.model.ReturnType;
import com.cgiser.sso.model.User;

public class ResetPassWordAction extends AbstractAction {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			ReturnType<String> returnType = new ReturnType<String>();
			String userName = ServletUtil.getDefaultValue(request, "user", ""); 
			String pwd = ServletUtil.getDefaultValue(request, "pwd", ""); 
			String newPwd = ServletUtil.getDefaultValue(request, "newpwd", ""); 
			String newPwd1 = ServletUtil.getDefaultValue(request, "newpwd1", ""); 
			if(!StringUtils.checkUserNameValid(userName)){
				returnType.setStatus(0);
				returnType.setMsg("RESETPWD_USERNAMENOTVALID");
		    	super.printReturnType2Response(response, returnType);
		    	return null;
			}
			if(!StringUtils.checkUserNameValid(newPwd)){
				returnType.setStatus(0);
				returnType.setMsg("RESETPWD_NEWPWDNOTVALID");
		    	super.printReturnType2Response(response, returnType);
		    	return null;
			}
			if(!newPwd.equals(newPwd1)){
				returnType.setStatus(0);
				returnType.setMsg("RESETPWD_TWONOTEQUALS");
		    	super.printReturnType2Response(response, returnType);
		    	return null;
			}
			UserManager userManager = (UserManager) HttpSpringUtils.getBean("userManager");
			
			User user = userManager.getUserbyUserName(userName);
			if(user==null){
				returnType.setStatus(0);
				returnType.setMsg("RESETPWD_USERNOTEXIST");
		    	super.printReturnType2Response(response, returnType);
		    	return null;
			}
			if(user.getState()!=1){
				returnType.setStatus(0);
				returnType.setMsg("RESETPWD_USERSTATUSERROR");
		    	super.printReturnType2Response(response, returnType);
		    	return null;
			}
			if(!user.getPwd().equals(DigestUtils.digest(pwd).toUpperCase())){
				returnType.setStatus(0);
				returnType.setMsg("RESETPWD_PWDERROR");
		    	super.printReturnType2Response(response, returnType);
		    	return null;
			}
			if(userManager.updateUserPwdByUserName(userName, newPwd1)>0){
				returnType.setStatus(1);
				returnType.setMsg("RESETPWD_SUCESS");
		    	super.printReturnType2Response(response, returnType);
		    	return null;
			}

			returnType.setStatus(0);
			returnType.setMsg("RESETPWD_FAIL");
	    	super.printReturnType2Response(response, returnType);
	    	return null;
		}catch (Exception e) {
			logger.error("bind error:",e);
		}

		return null;
	}
}
