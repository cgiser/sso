package com.cgiser.sso.model;

public enum RegisterEnum {
	/** 注册成功 */
    REGISTER_SUCCESS(ExceptionConstants.SUCCESS, "注册成功"),

    /** 注册失败 */
    REGISTER_FAILURE(ExceptionConstants.FAILURE, "注册失败"),
    /** 用户昵称已经存在 */
    REGISTER_NICK_EXIST(ExceptionConstants.REGISTER_NICK_EXIST, "用户昵称已经存在"),
    
    /** 用户昵称不符合规范 */
    REGISTER_NICK_INVALIDATE(ExceptionConstants.REGISTER_NICK_INVALIDATE, "用户昵称不符合规范"),
    
    REGISTER_PASSWORD_INVALIDATE(ExceptionConstants.REGISTER_PASSWORD_INVALIDATE, "用户密码不符合规范");
    
    private int code;

    private String description;

    private RegisterEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getValue() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static RegisterEnum valueOf(int code) {

        if (code == ExceptionConstants.SUCCESS) {
            return REGISTER_SUCCESS;
        } else if (code == ExceptionConstants.FAILURE) {
            return REGISTER_FAILURE;
        } else if (code == ExceptionConstants.REGISTER_NICK_INVALIDATE) {
            return REGISTER_NICK_INVALIDATE;
        } else if (code == ExceptionConstants.REGISTER_NICK_EXIST) {
            return REGISTER_NICK_EXIST;
        } else if (code == ExceptionConstants.REGISTER_PASSWORD_INVALIDATE) {
            return REGISTER_PASSWORD_INVALIDATE;
        } else {
            return REGISTER_FAILURE;
        }
    }
}
