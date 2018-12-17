package com.sqber.personMgr.dal;

import java.util.List;

import com.sqber.personMgr.entity.User;

public interface IUserRepository {

	User getByID(int userId);

	User getByName(String userCode);

	int insertUser(User userinfo);

	void updateUserInfo(User user);

	void updateImagePath(User user);

	String getImagePath(String userCode);
	
	public List<User> getAll();
	
	User getByUserName(String userName);
	
	List<User> getUserList(User user);
	
	public void addUser(User user);
	
	public void updateUser(User user);
	
	public void deleteUser(User user);
	
	public void restartUserPassword(User user);
}
