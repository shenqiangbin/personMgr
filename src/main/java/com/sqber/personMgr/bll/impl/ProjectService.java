package com.sqber.personMgr.bll.impl;

import com.sqber.personMgr.base.SessionHelper;
import com.sqber.personMgr.bll.IProjectService;
import com.sqber.personMgr.dal.ProjectMapper;
import com.sqber.personMgr.entity.Project;
import com.sqber.personMgr.entity.query.ProjectQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProjectService implements IProjectService {

    @Autowired
    private ProjectMapper projectRepository;

    @Override
    public int addProject(Project model) {

        model.setStatus(1);
        model.setCreatetime(new Date());
        model.setModifytime(new Date());

        if (SessionHelper.IsUserInfoExsit()){
            model.setCreateuser(SessionHelper.GetLoginUserCode());
            model.setModifyuser(SessionHelper.GetLoginUserCode());
        }

        return projectRepository.insert(model);
    }

    @Override
    public void updateById(Project model) {
        model.setModifytime(new Date());
        if (SessionHelper.IsUserInfoExsit()){
            model.setModifyuser(SessionHelper.GetLoginUserCode());
        }
        projectRepository.updateByPrimaryKey(model);
    }

    @Override
    public Project getByID(int id) {
        return projectRepository.selectByPrimaryKey(id);
    }

    @Override
    public List<Project> getList(ProjectQuery query) {
        return projectRepository.getList(query);
    }

    public void removeProject(String ids){
        String currentUser = "";
        if (SessionHelper.IsUserInfoExsit()){
            currentUser = SessionHelper.GetLoginUserCode();
        }
        String[] s = ids.split(",");
        projectRepository.removeProject(currentUser,s);
    }
}
