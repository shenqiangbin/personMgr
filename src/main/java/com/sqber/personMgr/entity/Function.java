package com.sqber.personMgr.entity;

import java.util.Date;

public class Function {
	
	private Integer functionID;

    private String menuID;

    private String funcTypeID;

    private Integer status;

    private String createUser;

    private Date createTime;

    private String modifyUser;

    private Date modifyTime;
    
    private String menuName;
    
	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getFunctionID() {
		return functionID;
	}

	public void setFunctionID(Integer functionID) {
		this.functionID = functionID;
	}

	public String getMenuID() {
		return menuID;
	}

	public void setMenuID(String menuID) {
		this.menuID = menuID;
	}

	public String getFuncTypeID() {
		return funcTypeID;
	}

	public void setFuncTypeID(String funcTypeID) {
		this.funcTypeID = funcTypeID;
	}

	@Override
	public String toString() {
		return "Function [functionID=" + functionID + ", menuID=" + menuID + ", funcTypeID=" + funcTypeID + ", status="
				+ status + ", createUser=" + createUser + ", createTime=" + createTime + ", modifyUser=" + modifyUser
				+ ", modifyTime=" + modifyTime + "]";
	}

	
}