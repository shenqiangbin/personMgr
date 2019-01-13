package com.sqber.personMgr.bll.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sqber.personMgr.base.PagedResponse;
import com.sqber.personMgr.base.SessionHelper;
import com.sqber.personMgr.base.StringUtil;
import com.sqber.personMgr.bll.ITaskService;
import com.sqber.personMgr.dal.ProjectMapper;
import com.sqber.personMgr.dal.TaskMapper;
import com.sqber.personMgr.entity.Project;
import com.sqber.personMgr.entity.Task;
import com.sqber.personMgr.entity.TaskListItem;
import com.sqber.personMgr.entity.query.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaskService implements ITaskService {

    @Autowired
    private TaskMapper taskRepository;
    @Autowired
    private ProjectMapper projectRepository;

    @Override
    public int addTask(Task model) {

        model.setStatus(1);
        model.setCreatetime(new Date());
        model.setModifytime(new Date());

        if (SessionHelper.IsUserInfoExsit()){
            model.setCreateuser(SessionHelper.GetLoginUserCode());
            model.setModifyuser(SessionHelper.GetLoginUserCode());
        }

        return taskRepository.insert(model);
    }

    @Override
    public void updateById(Task model) {
        model.setModifytime(new Date());
        if (SessionHelper.IsUserInfoExsit()){
            model.setModifyuser(SessionHelper.GetLoginUserCode());
        }
        taskRepository.updateByPrimaryKey(model);
    }

    @Override
    public Task getByID(int id) {
        return taskRepository.selectByPrimaryKey(id);
    }

    @Override
    public List<Task> getList(TaskQuery query) {
        return taskRepository.getList(query);
    }

    @Override
    public PagedResponse<TaskListItem> getItemList(TaskQuery query) {

        PageHelper.startPage(query.getCurrentPage(), query.getPageSize());
        List<Task> list = taskRepository.getList(query);
        PageInfo<Task> pageInfo = new PageInfo<>(list);

        long total= pageInfo.getTotal();
        int pages = pageInfo.getPages();

        PagedResponse<TaskListItem> pagedResponse = new PagedResponse<>();

        pagedResponse.setCurrentPage(query.getCurrentPage());
        pagedResponse.setPageSize(query.getPageSize());
        pagedResponse.setTotalCount(total);
        pagedResponse.setTotalPage(pages);
        pagedResponse.setList(converToListItem(list));

        return pagedResponse;
    }

    private List<TaskListItem> converToListItem(List<Task> list) {
        List<TaskListItem> result = new ArrayList<>();

        List<Project> projects = getProjects(list);

        for (Task t : list) {
            String projectName = getProjectName(t.getProjectcode(),projects);

            TaskListItem newItem =new TaskListItem(t);
            newItem.setProjectName(projectName);

            result.add(newItem);
        }


        return result;
    }

    private String getProjectName(String code,List<Project> projects){
        for(Project p : projects){
            if(p.getCode().equals(code))
                return p.getName();
        }
        return "";
    }

    private List<Project> getProjects(List<Task> list){
        List<String> projectCodes = new ArrayList<>();
        for (Task t : list) {
            if(StringUtil.isNotBlank(t.getProjectcode()) && !projectCodes.contains(t.getProjectcode())) //注意去重
                projectCodes.add(t.getProjectcode());
        }
        if(projectCodes.size() == 0)
            return new ArrayList<Project>();

        return projectRepository.getByCodes(projectCodes);
    }

    public void removeTask(String ids){
        String currentUser = "";
        if (SessionHelper.IsUserInfoExsit()){
            currentUser = SessionHelper.GetLoginUserCode();
        }
        String[] s = ids.split(",");
        taskRepository.removeTask(currentUser,s);
    }
}
