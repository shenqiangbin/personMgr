package com.sqber.personMgr.dal;

import com.sqber.personMgr.entity.Project;
import com.sqber.personMgr.entity.query.ProjectQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectMapper {
    int deleteByPrimaryKey(Integer projectid);

    int insert(Project record);

    Project selectByPrimaryKey(Integer projectid);

    Project getByCode(String code);

    List<Project> getByCodes(@Param("codes")List<String> codes);

    List<Project> selectAll();
    
    List<Project> getList(ProjectQuery query);

    int updateByPrimaryKey(Project record);

    void removeProject(String currentUser, String[] ids);
}