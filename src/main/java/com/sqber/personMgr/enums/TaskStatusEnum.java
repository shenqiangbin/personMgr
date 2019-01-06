package com.sqber.personMgr.enums;

import java.util.ArrayList;
import java.util.List;

import com.sqber.personMgr.base.DDLItem;

public enum TaskStatusEnum {
	None("未开始",0),Processing("进行中",1),Done("已完成",2),NoIdea("无法处理",3);
	
	private int value;
	public int getValue() {
		return this.value;
	}
	public void setValue(int value) {
		this.value = value;
	}

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //构造方法必须是private或者默认
    private TaskStatusEnum(String name,int value) {
        this.name = name;
        this.value = value;
    }

    public static TaskStatusEnum valueOf(int value) {
        switch (value) {
        case 0:
            return TaskStatusEnum.None;
        case 1:
            return TaskStatusEnum.Processing;
        case 2:
            return TaskStatusEnum.Done;
        case 3:
            return TaskStatusEnum.NoIdea;
        default:
            return null;
        }
    }
    
    public DDLItem toDDLItem() {
    	return new DDLItem(this.getValue(),this.getName());
    }
    
    public static List<DDLItem> toDDLItems() {
    	List<DDLItem> list =  new ArrayList<DDLItem>();
    	for(TaskStatusEnum t : TaskStatusEnum.values()) {
    		list.add(t.toDDLItem());
    	}
    	return list;
    }
}
