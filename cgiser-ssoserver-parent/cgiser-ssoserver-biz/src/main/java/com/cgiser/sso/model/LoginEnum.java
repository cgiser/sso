package com.cgiser.sso.model;
/**
 * 登录返回的状况说明
 * 
 * @author 杨光辉
 * @version $Id$
 */
public enum LoginEnum {

    /** 登陆成功 */
    LOGON_SUCCESS(ExceptionConstants.SUCCESS, "登录成功"),

    /** 登陆失败 */
    LOGON_FAILURE(ExceptionConstants.FAILURE, "登录失败"),

    /**
     * 登陆的邮箱没有注册 NOTICE:用户被删除的情况下，通过其邮箱查询时，应该查询不到，如果现有业务没有 这样处理，那需要FIX。
     */
    LOGON_USER_NOT_EXIST(ExceptionConstants.LOGON_USER_NOT_EXIST, "登录的用户没有注册"),

    /** 密码不正确 */
    LOGON_PASSWORD_WRONG(ExceptionConstants.LOGON_PASSWORD_WRONG, "密码不正确"),

    /** 该用户已经被冻结 */
    LOGON_USER_INVALIDATE(ExceptionConstants.LOGON_USER_INVALIDATE, "该用户已经被冻结"),

    /** 用户加密ID为空 */
    FAKE_LOGON_ID_EN_EMPTY(ExceptionConstants.FAKE_LOGON_ID_EN_EMPTY, "用户加密ID为空"),

    /** 用户加密ID为不存在 */
    LOGON_USERIDEN_NOT_EXIST(ExceptionConstants.USERIDEN_NOT_EXIST, "用户加密ID为不存在"),
    
    /** 登录ip已经被封 */
    LOGON_USERIP_BLOCK(ExceptionConstants.LOGON_USERIP_BLOCK, "登录的用户IP已经被封");

    private int code;

    private String description;

    private LoginEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getValue() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static LoginEnum valueOf(int code) {

        if (code == ExceptionConstants.SUCCESS) {
            return LOGON_SUCCESS;
        } else if (code == ExceptionConstants.FAILURE) {
            return LOGON_FAILURE;
        } else if (code == ExceptionConstants.LOGON_USER_NOT_EXIST) {
            return LOGON_USER_NOT_EXIST;
        } else if (code == ExceptionConstants.LOGON_PASSWORD_WRONG) {
            return LOGON_PASSWORD_WRONG;
        } else if (code == ExceptionConstants.LOGON_USER_INVALIDATE) {
            return LOGON_USER_INVALIDATE;
        } else if (code == ExceptionConstants.FAKE_LOGON_ID_EN_EMPTY) {
            return FAKE_LOGON_ID_EN_EMPTY;
        } else if (code == ExceptionConstants.USERIDEN_NOT_EXIST) {
            return LOGON_USERIDEN_NOT_EXIST;
        } else if (code == ExceptionConstants.LOGON_USERIP_BLOCK) {
            return LOGON_USERIP_BLOCK;
        } else {
            return LOGON_FAILURE;
        }
    }

}
