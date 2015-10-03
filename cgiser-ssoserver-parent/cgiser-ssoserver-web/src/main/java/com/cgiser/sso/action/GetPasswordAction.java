package com.cgiser.sso.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgiser.keel.http.utils.HttpSpringUtils;
import com.cgiser.sso.common.utils.ServletUtil;
import com.cgiser.sso.common.utils.StringUtils;
import com.cgiser.sso.manager.UserManager;
import com.cgiser.sso.model.ReturnType;
import com.cgiser.sso.model.User;

public class GetPasswordAction extends AbstractAction {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			ReturnType<String> returnType = new ReturnType<String>();
			String email = ServletUtil.getDefaultValue(request, "email", ""); 
			if(!StringUtils.checkUserEmailValid(email)){
				returnType.setStatus(0);
				returnType.setMsg("GETPASSWORD_EMAILERROR");
		    	super.printReturnType2Response(response, returnType);
		    	return null;
			}
			UserManager userManager = (UserManager) HttpSpringUtils.getBean("userManager");
			User user = userManager.getUserbyEmail(email);
			if(user==null){
				returnType.setStatus(0);
				returnType.setMsg("用户不存在");
		    	super.printReturnType2Response(response, returnType);
		    	return null;
			}
			if(user.getState()!=1){
				returnType.setStatus(0);
				returnType.setMsg("用户状态为不正常，请联系客服");
		    	super.printReturnType2Response(response, returnType);
		    	return null;
			}
			
			if(userManager.updateUserPwdByEmail(email, RandomStringUtils.randomAlphanumeric(8))>0){
				returnType.setStatus(1);
				returnType.setMsg("GETPASSWORD_SUCESS");
		    	super.printReturnType2Response(response, returnType);
		    	return null;
			}
			returnType.setStatus(0);
			returnType.setMsg("找回密码失败，请联系客服");
	    	super.printReturnType2Response(response, returnType);
	    	return null;
		}catch (Exception e) {
			logger.error("bind error:",e);
		}

		return null;
	}
}
