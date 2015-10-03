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
import com.cgiser.sso.manager.GameManager;
import com.cgiser.sso.model.Game;
import com.cgiser.sso.model.ReturnType;

public class GetGameServerByGameIden extends AbstractAction  {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			ReturnType<Game> returnType = new ReturnType<Game>();
			String gameIden = ServletUtil.getDefaultValue(request, "gameIden", ""); 
			if(StringUtils.checkNullWithTrim(gameIden)){
				returnType.setStatus(0);
				returnType.setMsg("GEMEIDEN_NULL");
		    	super.printReturnType2Response(response, returnType);
		    	return null;
			}
			GameManager gameManager = (GameManager)HttpSpringUtils.getBean("gameManager");
			Game game = gameManager.getGameByGameIden(gameIden);
			if(game==null){
				returnType.setStatus(0);
				returnType.setMsg("GEMEIDEN_INVALIDATE");
		    	super.printReturnType2Response(response, returnType);
		    	return null;
			}else{
				returnType.setStatus(1);
				returnType.setValue(game);
		    	super.printReturnType2Response(response, returnType);
		    	return null;
			}
		}catch (Exception e) {
			logger.error("bind error:",e);
		}

		return null;
	}
}
