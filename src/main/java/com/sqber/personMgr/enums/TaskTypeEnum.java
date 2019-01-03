package com.sqber.personMgr.enums;

public enum TaskTypeEnum {
	Demand(1),Bug(0);
	
	private int value;
	public int getValue() {
		return this.value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
    //构造方法必须是private或者默认
    private TaskTypeEnum(int value) {
        this.value = value;
    }

    public TaskTypeEnum valueOf(int value) {
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
