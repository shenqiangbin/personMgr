package com.sqber.personMgr.dal;

import java.util.List;

import com.sqber.personMgr.entity.UserRole;

public interface IBdUserRoleRepository {
	
	public List<UserRole> getByUserId(UserRole roletable);
		
	public void insert(UserRole roletable);
		
	public void delete(UserRole roletable);
		
	public void update(UserRole roletable);
		
	public UserRole getByUserAndRole(UserRole roletable);
	
}
