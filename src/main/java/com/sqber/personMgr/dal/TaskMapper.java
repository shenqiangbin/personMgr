package com.sqber.personMgr.dal;

import com.sqber.personMgr.entity.Task;
import java.util.List;

public interface TaskMapper {
    int deleteByPrimaryKey(Integer taskid);

    int insert(Task record);

    Task selectByPrimaryKey(Integer taskid);

    List<Task> selectAll();

    int updateByPrimaryKey(Task record);
}