package com.sqber.personMgr.entity;

import java.util.Date;

public class Role {
	
	private Integer roleID;

    private String roleCode;

    private String roleName;

    private Integer status;

    private String createUser;

    private Date createTime;

    private String modifyUser;

    private Date modifyTime;

	public Integer getRoleID() {
		return roleID;
	}

	public void setRoleID(Integer roleID) {
		this.roleID = roleID;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	@Override
	public String toString() {
		return "Role [roleID=" + roleID + ", roleCode=" + roleCode + ", roleName=" + roleName + ", status=" + status
				+ ", createUser=" + createUser + ", createTime=" + createTime + ", modifyUser=" + modifyUser
				+ ", modifyTime=" + modifyTime + "]";
	}

    
}