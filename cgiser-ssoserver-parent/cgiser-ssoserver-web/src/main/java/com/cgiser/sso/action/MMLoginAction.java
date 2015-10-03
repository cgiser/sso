package com.cgiser.sso.action;

import java.io.IOException;

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
import com.cgiser.sso.manager.UserManager;
import com.cgiser.sso.model.RegisterEnum;
import com.cgiser.sso.model.RegisterType;
import com.cgiser.sso.model.User;
import com.cgiser.sso.model.UserRegisterResult;

public class MMLoginAction extends AbstractAction {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String userId = ServletUtil.getDefaultValue(request, "userId", "");
		String key = ServletUtil.getDefaultValue(request, "key", "");
		String cpId = ServletUtil.getDefaultValue(request, "cpId", "");
		String cpServiceId = ServletUtil.getDefaultValue(request,
				"cpServiceId", "");
		String channelId = ServletUtil
				.getDefaultValue(request, "channelId", "");
		String p = ServletUtil.getDefaultValue(request, "p", "");
		String region = ServletUtil.getDefaultValue(request, "region", "");
		String Ua = ServletUtil.getDefaultValue(request, "Ua", "");
		response.setContentType("text/plain");
//		response.setContentLength(1);
		response.setCharacterEncoding("UTF-8");
		UserManager userManager = (UserManager) HttpSpringUtils
		.getBean("userManager");
		User user = userManager.getUserbyUserName(userId+"@mm.com");
		if(user==null){
			UserRegisterResult rResult = new UserRegisterResult(RegisterEnum.REGISTER_FAILURE, 0, null);
	    	String pwd = RandomStringUtils.randomAlphanumeric(8);
			rResult = userManager.register(userId+"@mm.com", pwd.getBytes(), "", "Android", "chs", "mm", RegisterType.MMLogin.getValue());
			if(rResult.isFailure()){
				try {
					response.getWriter().print("\r");
					response.getWriter().print("0");
					response.getWriter().close();
					response.getWriter().flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return null;
			}
			user = userManager.getUserbyUserName(userId+"@mm.com");
		}

		try {
			//response.getWriter().print("\r");
			response.getWriter().print("0");
			response.getWriter().close();
			response.getWriter().flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
