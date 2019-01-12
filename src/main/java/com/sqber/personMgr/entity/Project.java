package com.sqber.personMgr.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/*
* 日期问题涉及到 连接字符串，配置项和接收对象的配置等*/
public class Project {
    private Integer projectid;

    private String code;

    private String name;

    private String testurl;

    private String demourl;

    private String onlineurl;

    private String note;

    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date starttime;

    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date endtime;

    private Integer status;

    private String createuser;

    private Date createtime;

    private String modifyuser;

    private Date modifytime;

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser == null ? null : createuser.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getModifyuser() {
        return modifyuser;
    }

    public void setModifyuser(String modifyuser) {
        this.modifyuser = modifyuser == null ? null : modifyuser.trim();
    }

    public Date getModifytime() {
        return modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    public String getTesturl() {
        return testurl;
    }

    public void setTesturl(String testurl) {
        this.testurl = testurl;
    }

    public String getDemourl() {
        return demourl;
    }

    public void setDemourl(String demourl) {
        this.demourl = demourl;
    }

    public String getOnlineurl() {
        return onlineurl;
    }

    public void setOnlineurl(String onlineurl) {
        this.onlineurl = onlineurl;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}