package com.sqber.personMgr.entity;

import java.util.Date;


public class Menu {
	
	private Integer menuID;

    private String menuName;

    private String menuURL;

	private String icon;

	private String roles;

    private Integer status;

    private String createUser;

    private Date createTime;

    private String modifyUser;

    private Date modifyTime;

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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

	public String getMenuURL() {
		return menuURL;
	}

	public void setMenuURL(String menuURL) {
		this.menuURL = menuURL;
	}

	@Override
	public String toString() {
		return "Menu [menuID=" + menuID + ", menuName=" + menuName + ", menuURL=" + menuURL + ", status=" + status
				+ ", createUser=" + createUser + ", createTime=" + createTime + ", modifyUser=" + modifyUser
				+ ", modifyTime=" + modifyTime + "]";
	}

}