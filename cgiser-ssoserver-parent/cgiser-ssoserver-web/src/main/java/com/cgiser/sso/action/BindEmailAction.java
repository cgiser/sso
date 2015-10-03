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
import com.cgiser.sso.manager.UserManager;
import com.cgiser.sso.model.ReturnType;
import com.cgiser.sso.model.User;

public class BindEmailAction extends AbstractAction {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			ReturnType<String> returnType = new ReturnType<String>();
			String userIden = ServletUtil.getDefaultValue(request, "userIden", ""); 
			String email = ServletUtil.getDefaultValue(request, "email", ""); 
			if(!StringUtils.checkUserEmailValid(email)){
				returnType.setStatus(0);
				returnType.setMsg("BINDEMAIL_ERROR");
		    	super.printReturnType2Response(response, returnType);
		    	return null;
			}
			if(StringUtils.checkNullWithTrim(userIden)){
				returnType.setStatus(0);
				returnType.setMsg("BINDEMAIL_USERIDENNULL");
		    	super.printReturnType2Response(response, returnType);
		    	return null;
			}
			UserManager userManager = (UserManager) HttpSpringUtils.getBean("userManager");
			User user = userManager.getUserbyEmail(email);
			if(user!=null){
				returnType.setStatus(0);
				returnType.setMsg("BINDEMAIL_BUSY");
		    	super.printReturnType2Response(response, returnType);
		    	return null;
			}
			if(userManager.bindEmail(userIden, email)>0){
				returnType.setStatus(1);
				returnType.setMsg("BINDEMAIL_SUCESS");
		    	super.printReturnType2Response(response, returnType);
		    	return null;
			}else{
				returnType.setStatus(0);
				returnType.setMsg("BINDEMAIL_FAIL");
		    	super.printReturnType2Response(response, returnType);
		    	return null;
			}
		}catch (Exception e) {
			logger.error("bind error:",e);
		}

		return null;
	}
}
