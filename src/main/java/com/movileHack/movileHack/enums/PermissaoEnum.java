package com.movileHack.movileHack.enums;

public enum PermissaoEnum {
	
	ADMIN("ADMIN"),
	COMUM("COMUM");
	
	private String desc;
	
	private PermissaoEnum(String desc){
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}
