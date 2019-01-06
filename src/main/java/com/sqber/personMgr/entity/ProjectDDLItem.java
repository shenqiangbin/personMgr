package com.sqber.personMgr.entity;

import com.sqber.personMgr.entity.Project;

public class ProjectDDLItem {

	private String code;

    private String name;
    
    public ProjectDDLItem(Project model) {
    	
    	if(model!=null) {
        	this.code = model.getCode();
        	this.name = new StringBuilder().
        			append(model.getCode()).
        			append("-").
        			append(model.getName()).toString();
    	}
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
 
    
}
