package com.sqber.personMgr.bll.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;

import com.sqber.personMgr.bll.IUserService;
import com.sqber.personMgr.dal.IBdFunctionRepository;
import com.sqber.personMgr.dal.IBdRoleFuncRepository;
import com.sqber.personMgr.dal.IBdRoleRepository;
import com.sqber.personMgr.dal.IBdUserRoleRepository;
import com.sqber.personMgr.dal.ICustomerRepository;
import com.sqber.personMgr.dal.IUserRepository;
import com.sqber.personMgr.entity.Customer;
import com.sqber.personMgr.entity.Role;
import com.sqber.personMgr.entity.RoleFunc;
import com.sqber.personMgr.entity.User;
import com.sqber.personMgr.entity.UserRole;
import com.sqber.personMgr.entity.query.RoleMenuRequest;
import com.sqber.personMgr.entity.query.RoleResponse;
import com.sqber.personMgr.entity.query.RoleResponseItem;

@Service
public class UserService implements IUserService {
	
	@Autowired
	private IBdRoleRepository roleRepository;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IBdUserRoleRepository userRoleRepository;
	
	@Autowired
	private ICustomerRepository customerRepository;
	
	@Autowired
	private IBdFunctionRepository functionRepository;
	
	@Autowired
	private IBdRoleFuncRepository roleFuncRepository;


	@Override
	public List<User> getUserList(User user) {
		List<User> userList = userRepository.getUserList(user);
		if(null != userList && userList.size() > 0) {
			for (User user2 : userList) {
				UserRole userRole = new UserRole();
				userRole.setUserID(user2.getUserId());
				List<UserRole> roleList = userRoleRepository.getByUserId(userRole);
				if(null != roleList && roleList.size() > 0) {
					Role role = new Role();
					role.setRoleID(roleList.get(0).getRoleID());
					Role roles = roleRepository.getRoleById(role);
					if(null != roles) {
						user2.setRoleType(roles.getRoleName());
					}
					
				}
				
				Customer customer = customerRepository.getByID(user2.getCustomerID());
				if(null != customer) {
					user2.setCustomerName(customer.getCustomerName());
				}
			}
		}
		return userList;
	}

	@Override
	public void addUser(User user) {
		user.setCreateUser(user.getModifyUser());
		user.setCreateTime(new Date());
		user.setModifyTime(new Date());
		user.setPassword("111111");
		Role oldRole = new Role();
		oldRole.setRoleName(user.getRoleType());
		Role role = roleRepository.getRoleByName(oldRole);
		userRepository.addUser(user);
		if(null != role) {
			UserRole userRole = new UserRole();
			userRole.setCreateTime(new Date());
			userRole.setCreateUser(user.getCreateUser());
			userRole.setModifyUser(userRole.getCreateUser());
			userRole.setModifyTime(userRole.getCreateTime());
			userRole.setUserID(user.getUserId());
			userRole.setRoleID(role.getRoleID());
			userRoleRepository.insert(userRole);
		}
		
	}

	@Override
	public void delUser(String userIds,String userCode) {
		if(null != userIds && userIds.length() > 0) {
			String[] idArray = userIds.split(",");
			if(idArray.length > 0) {
				for (int i = 0; i < idArray.length; i++) {
					User user = new User();
					user.setUserId(Integer.parseInt(idArray[i]));
					user.setModifyTime(new Date());
					user.setModifyUser(userCode);
					UserRole existRole = new UserRole();
					existRole.setUserID(user.getUserId());
					UserRole userRole = new UserRole();
					userRole.setUserID(user.getUserId());
					userRoleRepository.delete(userRole);
					userRepository.deleteUser(user);
				}
			}
		}
		
	}
	
	@Override
	public void restart(String userIds,String userCode) {
		if(null != userIds && userIds.length() > 0) {
			String[] idArray = userIds.split(",");
			if(idArray.length > 0) {
				for (int i = 0; i < idArray.length; i++) {
					User user = new User();
					user.setUserId(Integer.parseInt(idArray[i]));
					user.setModifyTime(new Date());
					user.setModifyUser(userCode);
					user.setPassword("111111");
					userRepository.restartUserPassword(user);
				}
			}
		}
		
	}
	
	@Override
	public User getUser(Integer userId) {
		User user = userRepository.getByID(userId);
		if(null != user) {
			UserRole userRole = new UserRole();
			userRole.setUserID(user.getUserId());
			List<UserRole> roleList = userRoleRepository.getByUserId(userRole);
			if(null != roleList && roleList.size() > 0) {
				Role role = new Role();
				role.setRoleID(roleList.get(0).getRoleID());
				Role roles = roleRepository.getRoleById(role);
				if(null != roles) {
					user.setRoleType(roles.getRoleName());
				}
				
			}
		}
		return user;
	}

	@Override
	public boolean existUserCode(User user) {
		if(user.getUserId() > 0) {
			User exisUser = userRepository.getByName(user.getUserCode());
			if(null != exisUser) {
				if(exisUser.getUserId() == user.getUserId()) {
					return false;
				}else {
					return true;
				}
			}
		}else {
			User existUser = userRepository.getByName(user.getUserCode());
			if(null != existUser) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean existUserName(User user) {
		if(user.getUserId() > 0) {
			User exisUser = userRepository.getByUserName(user.getUserName());
			if(null != exisUser) {
				if(exisUser.getUserId() == user.getUserId()) {
					return false;
				}else {
					return true;
				}
			}
		}else {
			User existUser = userRepository.getByUserName(user.getUserName());
			if(null != existUser) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Role> getRoleListAll() {
		List<Role> list = roleRepository.getListAll();
		return list;
	}
	
	@Override
	public List<RoleResponse> getRoleDataList(String roleName) {
		Map<String,List<RoleResponseItem>> dataMap = new HashMap<String,List<RoleResponseItem>>();
		List<RoleResponse> list = new ArrayList<RoleResponse>();
		Role role = new Role();
		role.setRoleName(roleName);
		RoleFunc roleFunc = roleFuncRepository.getByRole(role);
		List<RoleResponseItem> datalist = functionRepository.getRoleList();
		if(null != datalist && datalist.size() > 0) {
			for (RoleResponseItem roleResponse : datalist){
				String menuId = roleResponse.getMenuID().toString();
				if(dataMap.containsKey(menuId)) {
					List<RoleResponseItem> functionList = dataMap.get(menuId);
					RoleResponseItem function = new RoleResponseItem();
					function.setFunctionID(roleResponse.getFunctionID());
					function.setFuncTypeID(roleResponse.getFuncTypeID());
					function.setMenuName(roleResponse.getMenuName());
					function.setFuncTypeName(roleResponse.getFuncTypeName());
					functionList.add(function);
					dataMap.put(menuId, functionList);
				}else {
					List<RoleResponseItem> functionList = new ArrayList<RoleResponseItem>();
					RoleResponseItem function = new RoleResponseItem();
					function.setFunctionID(roleResponse.getFunctionID());
					function.setFuncTypeID(roleResponse.getFuncTypeID());
					function.setMenuName(roleResponse.getMenuName());
					function.setFuncTypeName(roleResponse.getFuncTypeName());
					functionList.add(function);
					dataMap.put(menuId, functionList);
				}
			}
		}
		if(null != dataMap && dataMap.size() > 0) {
			for(Map.Entry<String,List<RoleResponseItem>> entry : dataMap.entrySet()){
			    String menuId = entry.getKey();
			    List<RoleResponseItem> itemList = entry.getValue();
			    if(null != itemList && itemList.size() > 0) {
			    	for (int i = 0; i < itemList.size(); i++) {
			    		RoleResponseItem roleResponseItem = itemList.get(i);
			    		if(null != roleFunc) {
			    			String functionIDs = roleFunc.getFunctionID();
			    			List<String> functionIDList = (List<String>) JSONArray.parse(functionIDs);
			    			if(null != functionIDList && functionIDList.size() > 0) {
								for (String string : functionIDList) {
									if(string.equals(roleResponseItem.getFunctionID().toString())) {
										roleResponseItem.setFlag(1);
										itemList.set(i, roleResponseItem);
									}
								}
							}
			    		}
					}
			    	String menuName = itemList.get(0).getMenuName();
			    	RoleResponse roleResponse = new RoleResponse();
			    	roleResponse.setMenuID(Integer.parseInt(menuId));
			    	roleResponse.setMenuName(menuName);
			    	roleResponse.setItemList(itemList);
			    	list.add(roleResponse);
			    }
			}
		}
		return list;
	}

	@Override
	public void updateRoleMenu(RoleMenuRequest roleMenuRequest) {
		Role role = new Role();
		role.setRoleName(roleMenuRequest.getRoleName());
		role = roleRepository.getRoleByName(role);
		if(null != role) {
			String functionID = roleMenuRequest.getFunctionID();
			RoleFunc roleFunc = roleFuncRepository.getByRoleId(role);
			if("1".equals(roleMenuRequest.getType())) {
				//选择
				if(null != roleFunc) {
					//udpate
					String typeIds = roleFunc.getFunctionID();
					List<String> functionIDList = (List<String>) JSONArray.parse(typeIds);
					if(null != functionIDList && functionIDList.size() > 0) {
						functionIDList.add(functionID);
						roleFunc.setFunctionID(JSONArray.toJSONString(functionIDList));
						roleFunc.setModifyTime(new Date());
						roleFunc.setModifyUser(roleMenuRequest.getModifyUser());
						roleFuncRepository.updateById(roleFunc);
					}
				}else {
					//新增
					List<String> functionIDList = new ArrayList<String>();
					functionIDList.add(functionID);
					RoleFunc rolef = new RoleFunc();
					rolef.setRoleID(role.getRoleID());
					rolef.setFunctionID(JSONArray.toJSONString(functionIDList));
					rolef.setCreateTime(new Date());
					rolef.setModifyTime(new Date());
					rolef.setModifyUser(roleMenuRequest.getModifyUser());
					rolef.setCreateUser(roleMenuRequest.getModifyUser());
					roleFuncRepository.add(rolef);
				}
			}else if("0".equals(roleMenuRequest.getType())){
				//取消
				if(null != roleFunc) {
					String typeIds = roleFunc.getFunctionID();
					List<String> functionIDList = (List<String>) JSONArray.parse(typeIds);
					if(null != functionIDList && functionIDList.size() > 0) {
						functionIDList.remove(functionID);
						if(functionIDList.size() > 0) {
							roleFunc.setFunctionID(JSONArray.toJSONString(functionIDList));
							roleFunc.setModifyTime(new Date());
							roleFunc.setModifyUser(roleMenuRequest.getModifyUser());
							roleFunc.setCreateUser(roleMenuRequest.getModifyUser());
							roleFuncRepository.updateById(roleFunc);
						}else {
							roleFuncRepository.deleteById(roleFunc);
						}
					}
					
				}
			}
		}
		
	}
	
}
