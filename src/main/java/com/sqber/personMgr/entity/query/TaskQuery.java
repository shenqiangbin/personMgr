package com.sqber.personMgr.entity.query;

public class TaskQuery extends PageQuery {

    private String sortStr;

    private String Content;

    public String getSortStr() {
        return sortStr;
    }

    public void setSortStr(String sortStr) {
        this.sortStr = sortStr;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
