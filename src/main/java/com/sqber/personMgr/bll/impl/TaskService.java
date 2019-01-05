package com.sqber.personMgr.bll.impl;

import com.sqber.personMgr.base.SessionHelper;
import com.sqber.personMgr.bll.ITaskService;
import com.sqber.personMgr.dal.TaskMapper;
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
    public List<TaskListItem> getItemList(TaskQuery query) {
        List<Task> list = taskRepository.getList(query);
        if (list != null) {
            return converToListItem(list);
        }
        return null;
    }

    private List<TaskListItem> converToListItem(List<Task> list) {
        List<TaskListItem> result = new ArrayList<>();
        for (Task t : list) {
            result.add(new TaskListItem(t));
        }
        return result;
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
