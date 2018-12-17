package com.sqber.personMgr.dal;

import java.util.List;

import com.sqber.personMgr.entity.Role;
import com.sqber.personMgr.entity.RoleFunc;

public interface IBdRoleFuncRepository {
	
	int add(RoleFunc roleFunc);
	
	RoleFunc getByRole(Role role);
	
	RoleFunc getByRoleId(Role role);
	
	public List<RoleFunc> getListAll(RoleFunc roleFunc);
	
	public void updateByRole(RoleFunc roleFunc);
	
	public void deleteById(RoleFunc roleFunc);
	
	public void updateById(RoleFunc roleFunc);
	
}
