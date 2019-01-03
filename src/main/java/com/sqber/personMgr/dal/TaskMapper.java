package com.sqber.personMgr.dal;

import com.sqber.personMgr.entity.Task;
import com.sqber.personMgr.entity.query.TaskQuery;

import java.util.List;

public interface TaskMapper {
    int deleteByPrimaryKey(Integer taskid);

    int insert(Task record);

    Task selectByPrimaryKey(Integer taskid);

    List<Task> selectAll();

    int updateByPrimaryKey(Task record);

    List<Task> getList(TaskQuery query);

    void removeTask(String currentUser, String[] ids);
}