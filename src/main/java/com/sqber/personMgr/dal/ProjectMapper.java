package com.sqber.personMgr.dal;

import com.sqber.personMgr.entity.Project;
import com.sqber.personMgr.entity.query.ProjectQuery;

import java.util.List;

public interface ProjectMapper {
    int deleteByPrimaryKey(Integer projectid);

    int insert(Project record);

    Project selectByPrimaryKey(Integer projectid);

    List<Project> selectAll();
    
    List<Project> getList(ProjectQuery query);

    int updateByPrimaryKey(Project record);
}