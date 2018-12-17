package com.sqber.personMgr.dal;

import java.util.List;

import com.sqber.personMgr.entity.Role;

public interface IBdRoleRepository {
	
	List<Role> getListAll();
	
	List<Role> getList(Role role);
	
	Role getRoleByName(Role role);
	
	Role getRoleById(Role role);
	
	Role existRole(Role role);
	
	public void addRole(Role role);
	
	public void updateRole(Role role);
	
	public void delRole(Role role);
	
}
