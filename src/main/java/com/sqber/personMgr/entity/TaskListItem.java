package com.sqber.personMgr.entity;

import com.sqber.personMgr.enums.TaskStatusEnum;
import com.sqber.personMgr.enums.TaskTypeEnum;

public class TaskListItem {

    private Task model;

    private String tasktypeDesc;
    private String taskstatusDesc;

    public TaskListItem(Task model) {
        this.model = model;
        this.tasktypeDesc = "";
        this.taskstatusDesc = "";
    }

    public String getTasktypeDesc() {
        if (this.model != null) {
            TaskTypeEnum model = TaskTypeEnum.valueOf(this.model.getTasktype());
            if (model != null)
                return model.getName();
        }
        return "";
    }

    public void setTasktypeDesc(String tasktypeDesc) {
        this.tasktypeDesc = tasktypeDesc;
    }

    public String getTaskstatusDesc() {
        if (this.model != null) {
            TaskStatusEnum model = TaskStatusEnum.valueOf(this.model.getTaskstatus());
            if (model != null)
                return model.getName();
        }
        return "";
    }


    public void setTaskstatusDesc(String taskstatusDesc) {
        this.taskstatusDesc = taskstatusDesc;
    }

    public Task getModel() {
        return model;
    }

    public void setModel(Task model) {
        this.model = model;
    }
}
