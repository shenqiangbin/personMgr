package com.sqber.personMgr.enums;

import java.util.ArrayList;
import java.util.List;

import com.sqber.personMgr.base.DDLItem;

public enum TaskTypeEnum {
	Demand("需求",1),Bug("BUG",0);
	
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
    private TaskTypeEnum(String name,int value) {
        this.name = name;
        this.value = value;
    }

    public static TaskTypeEnum valueOf(int value) {
        switch (value) {
        case 0:
            return TaskTypeEnum.Bug;
        case 1:
            return TaskTypeEnum.Demand;
        default:
            return null;
        }
    }
    
    @Override  
    public String toString() {  
        return this.value+"_"+this.name;  
    }  
    
    public DDLItem toDDLItem() {
    	return new DDLItem(this.getValue(),this.getName());
    }
    
    public static List<DDLItem> toDDLItems() {
    	
    	List<DDLItem> list =  new ArrayList<DDLItem>();
    	for(TaskTypeEnum t : TaskTypeEnum.values()) {
    		list.add(t.toDDLItem());
    	}
    	return list;
    }
}
