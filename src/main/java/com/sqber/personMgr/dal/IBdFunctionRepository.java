package com.sqber.personMgr.dal;

import java.util.List;

import com.sqber.personMgr.entity.Function;
import com.sqber.personMgr.entity.query.RoleResponseItem;

public interface IBdFunctionRepository {
	
	List<Function> getListAll();
	
	Function getRoleByName(Function function);
	
	List<RoleResponseItem> getRoleList();
	
}
