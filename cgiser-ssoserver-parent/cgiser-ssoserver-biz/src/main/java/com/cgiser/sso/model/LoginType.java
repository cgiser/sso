package com.cgiser.sso.model;

import org.apache.commons.lang.StringUtils;

public enum LoginType {
	DEVICE(1, "DEVICE"), USERPWD(2, "USERPWD"), Email(3, "Email"), Mobile(4,
			"Mobile"), NineOneLogin(5, "91Login"), QQLogin(6, "QQLogin"), WDJLogin(
			7, "WDJLogin"),BaiDuLogin(8, "BaiDuLogin"),MMLogin(9, "MMLogin");
	private int code;

	private String description;

	private LoginType(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getValue() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public static LoginType valueOf(int code) {
		switch (code) {
		case 1:
			return DEVICE;
		case 2:
			return USERPWD;
		case 3:
			return Email;
		case 4:
			return Mobile;
		case 5:
			return NineOneLogin;
		case 6:
			return QQLogin;
		case 7:
			return WDJLogin;
		case 8:
			return BaiDuLogin;
		case 9:
			return MMLogin;
		default:
			return null;
		}
	}

	public static LoginType valueFromDesc(String desc) {
		if (StringUtils.equalsIgnoreCase("DEVICE", desc)) {
			return DEVICE;
		}
		if (StringUtils.equalsIgnoreCase("USERPWD", desc)) {
			return USERPWD;
		}
		if (StringUtils.equalsIgnoreCase("EMAIL", desc)) {
			return Email;
		}
		if (StringUtils.equalsIgnoreCase("MOBILE", desc)) {
			return Mobile;
		}
		if (StringUtils.equalsIgnoreCase("91Login", desc)) {
			return NineOneLogin;
		}
		if (StringUtils.equalsIgnoreCase("QQLogin", desc)) {
			return QQLogin;
		}
		if (StringUtils.equalsIgnoreCase("WDJLogin", desc)) {
			return WDJLogin;
		}
		if (StringUtils.equalsIgnoreCase("BaiDuLogin", desc)) {
			return BaiDuLogin;
		}
		if (StringUtils.equalsIgnoreCase("MMLogin", desc)) {
			return MMLogin;
		}
		return null;
	}
}
