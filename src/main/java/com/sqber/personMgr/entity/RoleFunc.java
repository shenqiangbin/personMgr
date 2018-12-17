package com.sqber.personMgr.entity;

import java.util.Date;

public class RoleFunc {
	
    private Integer roleFuncID;

    private Integer roleID;

    private String functionID;

    private Integer status;

    private String createUser;

    private Date createTime;

    private String modifyUser;

    private Date modifyTime;
    
    private String type;
    
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getRoleID() {
		return roleID;
	}

	public void setRoleID(Integer roleID) {
		this.roleID = roleID;
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

	public Integer getRoleFuncID() {
		return roleFuncID;
	}

	public void setRoleFuncID(Integer roleFuncID) {
		this.roleFuncID = roleFuncID;
	}

	public String getFunctionID() {
		return functionID;
	}

	public void setFunctionID(String functionID) {
		this.functionID = functionID;
	}

	@Override
	public String toString() {
		return "RoleFunc [roleFuncID=" + roleFuncID + ", roleID=" + roleID + ", functionID=" + functionID + ", status="
				+ status + ", createUser=" + createUser + ", createTime=" + createTime + ", modifyUser=" + modifyUser
				+ ", modifyTime=" + modifyTime + ", type=" + type + "]";
	}

	

}