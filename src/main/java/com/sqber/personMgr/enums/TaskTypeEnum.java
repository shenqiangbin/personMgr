package com.sqber.personMgr.enums;

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
}
