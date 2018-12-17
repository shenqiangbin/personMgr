package com.sqber.personMgr.entity;

import java.util.Date;

public class BaseEntity {

    private Integer Status;
    private String CreateUser;
    private Date CreateTime;
    private String ModifyUser;
    private Date ModifyTime;

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public String getCreateUser() {
        return CreateUser;
    }

    public void setCreateUser(String createUser) {
        CreateUser = createUser;
    }

    public Date getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Date createTime) {
        CreateTime = createTime;
    }

    public String getModifyUser() {
        return ModifyUser;
    }

    public void setModifyUser(String modifyUser) {
        ModifyUser = modifyUser;
    }

    public Date getModifyTime() {
        return ModifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        ModifyTime = modifyTime;
    }
}
