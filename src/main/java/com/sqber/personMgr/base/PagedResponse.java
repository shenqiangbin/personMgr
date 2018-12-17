package com.sqber.personMgr.base;


import com.github.pagehelper.PageInfo;

import java.util.List;

public class PagedResponse<T> {
    public List<T> list;
    public int currentPage;
    public int totalPage;
    public long totalCount;
    public int pageSize;

    public PagedResponse() {
    }

    public PagedResponse(List<T> list,int currentPage,int pageSize){

        PageInfo<T> pageInfo = new PageInfo<>(list);

        long total= pageInfo.getTotal();
        int pages = pageInfo.getPages();

        this.setCurrentPage(currentPage);
        this.setPageSize(pageSize);

        this.setTotalCount(total);
        this.setTotalPage(pages);
        this.setList(list);
    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
