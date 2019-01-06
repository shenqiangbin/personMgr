package com.sqber.personMgr.base;

public class DDLItem {
	
	private int code;
	private String name;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public DDLItem(int code,String name) {
		this.code = code;
		this.name = name;
	}
}
