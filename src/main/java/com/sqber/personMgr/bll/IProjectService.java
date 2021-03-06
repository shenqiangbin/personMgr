package com.sqber.personMgr.bll;

import java.util.List;

import com.sqber.personMgr.entity.Project;
import com.sqber.personMgr.entity.ProjectDDLItem;
import com.sqber.personMgr.entity.query.ProjectQuery;

public interface IProjectService {

    int addProject(Project model) throws Exception;

    void updateById(Project model);

    Project getByID(int id);

    Project getByCode(String code);

    List<Project> getList(ProjectQuery query);
    
    List<ProjectDDLItem> getDDlItem();

    void removeProject(String ids);
}
