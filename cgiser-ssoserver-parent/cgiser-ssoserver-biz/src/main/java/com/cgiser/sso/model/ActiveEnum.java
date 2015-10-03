package com.cgiser.sso.model;

public enum ActiveEnum {
	 /** 激活成功 */
    ACTIVE_SUCCESS(1, "激活成功"),

    /** 激活失败 */
    ACTIVE_FAILURE(0, "激活失败");
    private int code;

    private String description;

    private ActiveEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getValue() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
