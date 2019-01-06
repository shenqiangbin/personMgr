package com.sqber.personMgr.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Task {
    private Integer taskid;

    private String title;

    private Integer tasktype;

    private Integer taskstatus;

    private String projectcode;

    private Integer moduleid;

    private String demandor;

    private String assignto;

    private String solver;

    private String content;

    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date puttime;

    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date scheduledstart;

    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date scheduledend;

    private Integer status;

    private String createuser;

    private Date createtime;

    private String modifyuser;

    private Date modifytime;

    public Integer getTaskid() {
        return taskid;
    }

    public void setTaskid(Integer taskid) {
        this.taskid = taskid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getTasktype() {
        return tasktype;
    }

    public void setTasktype(Integer tasktype) {
        this.tasktype = tasktype;
    }

    public Integer getTaskstatus() {
        return taskstatus;
    }

    public void setTaskstatus(Integer taskstatus) {
        this.taskstatus = taskstatus;
    }

    public String getProjectcode() {
        return projectcode;
    }

    public void setProjectcode(String projectcode) {
        this.projectcode = projectcode == null ? null : projectcode.trim();
    }

    public Integer getModuleid() {
        return moduleid;
    }

    public void setModuleid(Integer moduleid) {
        this.moduleid = moduleid;
    }

    public String getDemandor() {
        return demandor;
    }

    public void setDemandor(String demandor) {
        this.demandor = demandor == null ? null : demandor.trim();
    }

    public String getAssignto() {
        return assignto;
    }

    public void setAssignto(String assignto) {
        this.assignto = assignto == null ? null : assignto.trim();
    }

    public String getSolver() {
        return solver;
    }

    public void setSolver(String solver) {
        this.solver = solver == null ? null : solver.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getPuttime() {
        return puttime;
    }

    public void setPuttime(Date puttime) {
        this.puttime = puttime;
    }

    public Date getScheduledstart() {
        return scheduledstart;
    }

    public void setScheduledstart(Date scheduledstart) {
        this.scheduledstart = scheduledstart;
    }

    public Date getScheduledend() {
        return scheduledend;
    }

    public void setScheduledend(Date scheduledend) {
        this.scheduledend = scheduledend;
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
}