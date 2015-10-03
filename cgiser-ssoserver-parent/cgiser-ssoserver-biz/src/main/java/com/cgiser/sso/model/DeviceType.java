package com.cgiser.sso.model;

public enum DeviceType {
	Android(1, "Android"), Ios(2, "Ios"), Win8(3, "win8"), Nom(4,"Nom");
	private int code;

	private String description;

	private DeviceType(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getValue() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static DeviceType valueOf(int code) {
		if(code == 1){
			return DeviceType.Android;
		}else if(code == 2){
			return DeviceType.Ios;
		}else if(code == 3){
			return DeviceType.Win8;
		}else{
			return DeviceType.Nom;
		}
	}
}
