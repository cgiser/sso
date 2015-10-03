package com.cgiser.sso.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.Action;

import com.cgiser.sso.model.ReturnType;


public class AbstractAction extends Action {
	
    /**
     * 打印返回值给客户端
     * 
     * @param response
     * @param returnType
     * @throws IOException
     */
    protected void printReturnType2Response(HttpServletResponse response,
            ReturnType<?> returnType) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(JSONObject.fromObject(returnType));
    }
}
