package com.cgiser.sso.model;

public enum RegisterType {
	
	udid(1, "Udid"), user(2,"User"),email(3,"Email"),mobile(4,"Mobile"),NineOneLogin(5,"91Login"),QQLogin(6,"QQLogin"),WDJLogin(7,"WDJLogin"),BaiDuLogin(8, "BaiDuLogin"),MMLogin(9,"MMLogin");
	private int code;

	private String description;

	private RegisterType(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getValue() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static RegisterType valueOf(int code) {
		if(code == 1){
			return RegisterType.udid;
		}else if(code == 2){
			return RegisterType.user;
		}else if(code == 3){
			return RegisterType.email;
		}else if(code == 4) {
			return RegisterType.mobile;
		}else if(code == 5) {
			return RegisterType.NineOneLogin;
		}else if(code == 6) {
			return RegisterType.QQLogin;
		}else if(code == 7) {
			return RegisterType.WDJLogin;
		}else if(code == 8) {
			return RegisterType.BaiDuLogin;
		}else if(code == 9) {
			return RegisterType.MMLogin;
		}else{
			return RegisterType.udid;
		}
	}
}
