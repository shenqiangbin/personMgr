package com.sqber.personMgr.enums;

public enum RoleType {
	
	Admin("系统管理员", 1), 
	DataManager("系统数据员", 2), 
	User("客户", 3);
	
	private final  String name;
	
	private  Integer value;
	
	public String getName() {
		return name;
	}

	public final Integer getValue() {
		return value;
	}
	
	private RoleType(String name, Integer value) {
		this.name = name;
		this.value = value;
	}
}
