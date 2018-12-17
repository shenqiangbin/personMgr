package com.sqber.personMgr.dal;

import java.util.List;

import com.sqber.personMgr.entity.FuncType;

public interface IBdFuncTypeRepository {
	
	List<FuncType> getListAll();
	
	FuncType getById(FuncType funcType);
	
}
