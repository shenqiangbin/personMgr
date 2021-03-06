package com.sqber.personMgr.bll.impl;

import com.sqber.personMgr.base.SessionHelper;
import com.sqber.personMgr.bll.IProjectService;
import com.sqber.personMgr.dal.ProjectMapper;
import com.sqber.personMgr.entity.Project;
import com.sqber.personMgr.entity.ProjectDDLItem;
import com.sqber.personMgr.entity.query.ProjectQuery;
import com.sqber.personMgr.myException.ProjectCodeExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProjectService implements IProjectService {

    @Autowired
    private ProjectMapper projectRepository;

    @Override
    public int addProject(Project model) throws Exception {

        if(existModelCode(model))
            throw new ProjectCodeExistException("编码已存在:"+model.getCode());

        model.setStatus(1);
        model.setCreatetime(new Date());
        model.setModifytime(new Date());

        if (SessionHelper.IsUserInfoExsit()){
            model.setCreateuser(SessionHelper.GetLoginUserCode());
            model.setModifyuser(SessionHelper.GetLoginUserCode());
        }

        return projectRepository.insert(model);
    }

    private boolean existModelCode(Project model){
        Project dbModel = this.getByCode(model.getCode());
        if(dbModel!=null){
            if(dbModel.getProjectid() != model.getProjectid())
                return  true;
            else
                return  false;
        }
        else{
            return false;
        }
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
    public Project getByCode(String code) {
        return projectRepository.getByCode(code);
    }

    @Override
    public List<Project> getList(ProjectQuery query) {
        return projectRepository.getList(query);
    }

    @Override
    public List<ProjectDDLItem> getDDlItem(){
    	List<Project> list = projectRepository.getList(null);
    	
    	List<ProjectDDLItem> result = new ArrayList<ProjectDDLItem>();
    	for(Project item : list) {
    		result.add(new ProjectDDLItem(item));
    	}
    	
    	return result;
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
