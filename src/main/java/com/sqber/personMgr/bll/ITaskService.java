package com.sqber.personMgr.bll;

import com.sqber.personMgr.entity.Task;
import com.sqber.personMgr.entity.TaskListItem;
import com.sqber.personMgr.entity.query.TaskQuery;

import java.util.List;

public interface ITaskService {

    int addTask(Task model);

    void updateById(Task model);

    Task getByID(int id);

    List<Task> getList(TaskQuery query);

    List<TaskListItem> getItemList(TaskQuery query);

    void removeTask(String ids);
}
