package com.sqber.personMgr.enums;

public enum TaskStatusEnum {
	None(0),Processing(1),Done(2),NoIdea(3);
	
	private int value;
	public int getValue() {
		return this.value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
    //构造方法必须是private或者默认
    private TaskStatusEnum(int value) {
        this.value = value;
    }

    public TaskStatusEnum valueOf(int value) {
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
}
