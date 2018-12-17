package com.sqber.personMgr.entity.query;

public class RoleResponseItem {
	
	public Integer menuID;
	
	public String menuName;
	
	private Integer functionID;
	
	private String funcTypeID;
	
	private String funcTypeName;
	
	private int flag;
	
	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getFuncTypeName() {
		return funcTypeName;
	}

	public void setFuncTypeName(String funcTypeName) {
		this.funcTypeName = funcTypeName;
	}

	public Integer getMenuID() {
		return menuID;
	}

	public void setMenuID(Integer menuID) {
		this.menuID = menuID;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Integer getFunctionID() {
		return functionID;
	}

	public void setFunctionID(Integer functionID) {
		this.functionID = functionID;
	}

	public String getFuncTypeID() {
		return funcTypeID;
	}

	public void setFuncTypeID(String funcTypeID) {
		this.funcTypeID = funcTypeID;
	}

	@Override
	public String toString() {
		return "RoleResponseItem [menuID=" + menuID + ", menuName=" + menuName + ", functionID=" + functionID
				+ ", funcTypeID=" + funcTypeID + ", funcTypeName=" + funcTypeName + ", flag=" + flag + "]";
	}
	
}
