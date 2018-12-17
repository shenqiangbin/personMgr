package com.sqber.personMgr.entity;

import java.util.Date;

public class UserRole {
	
    private Integer userRoleID;

    private Integer roleID;

    private Integer userID;

    private Integer status;

    private String createUser;

    private Date createTime;

    private String modifyUser;

    private Date modifyTime;

	public Integer getUserRoleID() {
		return userRoleID;
	}

	public void setUserRoleID(Integer userRoleID) {
		this.userRoleID = userRoleID;
	}

	public Integer getRoleID() {
		return roleID;
	}

	public void setRoleID(Integer roleID) {
		this.roleID = roleID;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
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
		return "UserRole [userRoleID=" + userRoleID + ", roleID=" + roleID + ", userID=" + userID + ", status=" + status
				+ ", createUser=" + createUser + ", createTime=" + createTime + ", modifyUser=" + modifyUser
				+ ", modifyTime=" + modifyTime + "]";
	}
    
	
}