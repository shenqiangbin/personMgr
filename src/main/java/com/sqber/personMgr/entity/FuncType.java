package com.sqber.personMgr.entity;

import java.util.Date;


public class FuncType {
	
	private Integer funcTypeID;

    private String funcTypeName;

    private String funcTypeCode;

    private Integer status;

    private String createUser;

    private Date createTime;

    private String modifyUser;

    private Date modifyTime;
    
    private int flag = 0;
    
	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
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

	public Integer getFuncTypeID() {
		return funcTypeID;
	}

	public void setFuncTypeID(Integer funcTypeID) {
		this.funcTypeID = funcTypeID;
	}

	public String getFuncTypeName() {
		return funcTypeName;
	}

	public void setFuncTypeName(String funcTypeName) {
		this.funcTypeName = funcTypeName;
	}

	public String getFuncTypeCode() {
		return funcTypeCode;
	}

	public void setFuncTypeCode(String funcTypeCode) {
		this.funcTypeCode = funcTypeCode;
	}

	@Override
	public String toString() {
		return "FuncType [funcTypeID=" + funcTypeID + ", funcTypeName=" + funcTypeName + ", funcTypeCode="
				+ funcTypeCode + ", status=" + status + ", createUser=" + createUser + ", createTime=" + createTime
				+ ", modifyUser=" + modifyUser + ", modifyTime=" + modifyTime + "]";
	}

	
}