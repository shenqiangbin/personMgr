package com.sqber.personMgr.entity.query;

import java.util.List;

public class RoleResponse {
	
	public Integer menuID;
	
	public String menuName;
	
	private Integer functionID;
	
	private String funcTypeID;
	
	private String funcTypeName;
	
	public List<RoleResponseItem> itemList;

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

	public String getFuncTypeName() {
		return funcTypeName;
	}

	public void setFuncTypeName(String funcTypeName) {
		this.funcTypeName = funcTypeName;
	}

	public List<RoleResponseItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<RoleResponseItem> itemList) {
		this.itemList = itemList;
	}

	@Override
	public String toString() {
		return "RoleResponse [menuID=" + menuID + ", menuName=" + menuName + ", functionID=" + functionID
				+ ", funcTypeID=" + funcTypeID + ", funcTypeName=" + funcTypeName + ", itemList=" + itemList + "]";
	}
	
}
