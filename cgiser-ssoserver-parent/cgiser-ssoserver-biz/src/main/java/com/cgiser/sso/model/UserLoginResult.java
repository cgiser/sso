package com.cgiser.sso.model;

import java.io.Serializable;
/**
 * 用户登陆返回的结果封装
 * 
 * @author yangh
 * @version $Id$
 */
public class UserLoginResult implements Serializable {
    private static final long serialVersionUID = 4959189983143682623L;

    /** 业务处理状态 */
    private LoginEnum businessResult = LoginEnum.LOGON_SUCCESS;

    /** 业务处理状态的子状态、系统状态 */
    private String resultCode = "1";

    /** 登录成功之后，用户对象 */
    private UserGameDevice businessObject = null;
    
    /**
     * 构造函数
     * 
     * @param logonEnum
     * @param resultCode
     * @param user
     */
    public UserLoginResult(LoginEnum businessResult, int resultCode, UserGameDevice businessObject) {
        this.businessResult = businessResult;
        this.resultCode = Integer.toHexString(resultCode);
        this.businessObject = businessObject;
    }

    /**
     * 检测用户登录是否成功
     * 
     * @return
     */
    public boolean isSuccess() {
        return businessResult == LoginEnum.LOGON_SUCCESS;
    }

    /**
     * 检查用户登录是否失败
     * 
     * @return
     */
    public boolean isFailure() {
        return businessResult != LoginEnum.LOGON_SUCCESS;
    }

    public LoginEnum getBusinessResult() {
        return businessResult;
    }

    public void setBusinessResult(LoginEnum businessResult) {
        this.businessResult = businessResult;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = Integer.toHexString(resultCode);
    }

    public UserGameDevice getBusinessObject() {
        return businessObject;
    }

    public void setBusinessObject(UserGameDevice user) {
        this.businessObject = user;
    }
}
