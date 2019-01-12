package com.sqber.personMgr.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class LoginUserInfo extends User{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7631831018668118989L;
	private int userID;
	
	private String userCode;

	private String displayName;
	
	private String ssoUserID;
	
	private List<String> dataList; //数据表权限
	
	private List<String> dicList; //词典权限
	
	private List<String> themeList; //主题权限
	
	private List<String> knowList; //知识领域权限
	
	private Integer roleId;
	
	private Integer customerId;
	
	public LoginUserInfo(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		// TODO Auto-generated constructor stub
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getSsoUserID() {
		return ssoUserID;
	}

	public void setSsoUserID(String ssoUserID) {
		this.ssoUserID = ssoUserID;
	}

	public List<String> getDataList() {
		return dataList;
	}

	public void setDataList(List<String> dataList) {
		this.dataList = dataList;
	}

	public List<String> getDicList() {
		return dicList;
	}

	public void setDicList(List<String> dicList) {
		this.dicList = dicList;
	}

	public List<String> getThemeList() {
		return themeList;
	}

	public void setThemeList(List<String> themeList) {
		this.themeList = themeList;
	}

	public List<String> getKnowList() {
		return knowList;
	}

	public void setKnowList(List<String> knowList) {
		this.knowList = knowList;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	

}
