package com.sqber.personMgr.bll;

import java.util.List;

import com.sqber.personMgr.entity.Role;
import com.sqber.personMgr.entity.User;
import com.sqber.personMgr.entity.query.RoleMenuRequest;
import com.sqber.personMgr.entity.query.RoleResponse;

public interface IUserService {
	
	public List<User> getUserList(User user);
	
	public User getUser(Integer userId);
	
	public void addUser(User user);
	
	public void delUser(String userIds,String userCode);
	
	public boolean existUserCode(User user);
	
	public boolean existUserName(User user);
	
	public List<Role> getRoleListAll();
	
	public void restart(String userIds,String userCode);
	
	public List<RoleResponse> getRoleDataList(String roleName);
	
	public void updateRoleMenu(RoleMenuRequest roleMenuRequest);
	
}
