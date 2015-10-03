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
import com.cgiser.sso.model.ActiveResult;
import com.cgiser.sso.model.ReturnType;

public class ActiveLogonIdAction extends AbstractAction {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			ReturnType<ActiveResult> returnType = new ReturnType<ActiveResult>();
			String userIden = ServletUtil.getDefaultValue(request, "userIden", ""); 
			String logonId = ServletUtil.getDefaultValue(request, "logonId", ""); 
//			String idType = ServletUtil.getDefaultValue(request, "idType", ""); 
			String activeCode = ServletUtil.getDefaultValue(request, "activeCode", ""); 
			if(!StringUtils.checkUserEmailValid(logonId)){
				returnType.setStatus(0);
		    	super.printReturnType2Response(response, returnType);
		    	return null;
			}
			if(StringUtils.checkNullWithTrim(userIden)){
				returnType.setStatus(0);
		    	super.printReturnType2Response(response, returnType);
		    	return null;
			}
			if(StringUtils.checkNullWithTrim(activeCode)){
				returnType.setStatus(0);
		    	super.printReturnType2Response(response, returnType);
		    	return null;
			}
			UserManager userManager = (UserManager) HttpSpringUtils.getBean("userManager");
			ActiveResult result = userManager.activeUserEmail(logonId, userIden, activeCode);
			returnType.setValue(result);
			if(result.isSuccess()){
				returnType.setStatus(1);
				returnType.setMsg("ACTIVE_SUCESS");
			}else{
				returnType.setStatus(0);
				returnType.setMsg("ACTIVE_FAILURE");
			}
			super.printReturnType2Response(response, returnType);
		}catch (Exception e) {
			logger.error("active error:",e);
		}

		
		return null;
	}
}
