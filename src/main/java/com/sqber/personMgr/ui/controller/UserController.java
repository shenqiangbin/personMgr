package com.sqber.personMgr.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sqber.personMgr.bll.ICustomerService;
import com.sqber.personMgr.entity.query.SystemResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.sqber.personMgr.base.BaseResponse;
import com.sqber.personMgr.base.SessionHelper;
import com.sqber.personMgr.base.StringUtil;
import com.sqber.personMgr.bll.IUserService;
import com.sqber.personMgr.entity.Customer;
import com.sqber.personMgr.entity.Role;
import com.sqber.personMgr.entity.User;
import com.sqber.personMgr.entity.query.CustomerQuery;
import com.sqber.personMgr.entity.query.RoleMenuRequest;
import com.sqber.personMgr.entity.query.RoleResponse;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;
    
    @Autowired
    private ICustomerService customerService;

    /**
	 * 用户管理
	 * @param model
	 * @return
	 */
	@GetMapping("/index")
	public String userIndex(Model model) {
		return "user/user";
	}
	
	@ResponseBody
	@PostMapping(value = "/getUserData")
	public BaseResponse<SystemResponse> geUsertlist(HttpServletRequest request, HttpServletResponse response){
		BaseResponse<SystemResponse> result = new BaseResponse<SystemResponse>();
		if (!SessionHelper.IsUserInfoExsit()) {
			result.setCode(500);
			result.setMsg("用户未登录");
			return result;
		}
		try {
			PageHelper.startPage(Integer.parseInt(request.getParameter("currentPage")),Integer.parseInt(request.getParameter("pageSize")));
			User user = new User();
			if(!StringUtil.isBlank(request.getParameter("userName"))){
				user.setUserName(request.getParameter("userName"));
			}
			List<User> dataList = userService.getUserList(user);
			PageInfo<User> pageInfo = new PageInfo<User>(dataList);
			long total= pageInfo.getTotal();
			int pages = pageInfo.getPages();
			SystemResponse systemResponse = new SystemResponse();
			systemResponse.setUserList(pageInfo.getList());
			systemResponse.setCurrentPage(Integer.parseInt(request.getParameter("currentPage")));
			systemResponse.setTotalCount(total);
			systemResponse.setTotalPage(pages);
			systemResponse.setPageSize(Integer.parseInt(request.getParameter("pageSize")));
			result.setCode(200);
			result.setData(systemResponse);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setMsg("服务器错误");
		}
		return result;
	}
	
	
	@ResponseBody
	@PostMapping(value = "/addUser")
	public BaseResponse<SystemResponse> addUser(HttpServletRequest request,HttpServletResponse response){
		BaseResponse<SystemResponse> result = new BaseResponse<SystemResponse>();
		if (!SessionHelper.IsUserInfoExsit()) {
			result.setCode(500);
			result.setMsg("用户未登录");
			return result;
		}
		try {
			User user = new User();
			user.setModifyUser(SessionHelper.GetLoginUserCode());
			if(!StringUtil.isBlank(request.getParameter("userName"))){
				user.setUserName(request.getParameter("userName"));
			}
			if(!StringUtil.isBlank(request.getParameter("userCode"))){
				user.setUserCode(request.getParameter("userCode"));
			}
			if(!StringUtil.isBlank(request.getParameter("roleType"))){
				user.setRoleType(request.getParameter("roleType"));
			}
			if(!StringUtil.isBlank(request.getParameter("userId"))){
				user.setUserId(Integer.parseInt(request.getParameter("userId")));
			}
			if(!StringUtil.isBlank(request.getParameter("customerID"))){
				user.setCustomerID(Integer.parseInt(request.getParameter("customerID")));
			}
			if(userService.existUserCode(user)) {
				result.setCode(500);
				result.setMsg("用户编码不能重复");
				return result;
			}
			if(userService.existUserName(user)) {
				result.setCode(500);
				result.setMsg("用户名不能重复");
				return result;
			}
			userService.addUser(user);
			SystemResponse systemResponse = new SystemResponse();
			result.setCode(200);
			result.setData(systemResponse);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setMsg("服务器错误");
		}
		return result;
	}
	
	@ResponseBody
	@PostMapping(value = "/deleteUser")
	public BaseResponse<SystemResponse> deleteUser(HttpServletRequest request,HttpServletResponse response){
		BaseResponse<SystemResponse> result = new BaseResponse<SystemResponse>();
		if (!SessionHelper.IsUserInfoExsit()) {
			result.setCode(500);
			result.setMsg("用户未登录");
			return result;
		}
		try {
			userService.delUser(request.getParameter("customerIds"),SessionHelper.GetLoginUserCode());
			SystemResponse systemResponse = new SystemResponse();
			result.setCode(200);
			result.setData(systemResponse);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setMsg("服务器错误");
		}
		return result;
	}
	
	@ResponseBody
	@PostMapping(value = "/restart")
	public BaseResponse<SystemResponse> restart(HttpServletRequest request,HttpServletResponse response){
		BaseResponse<SystemResponse> result = new BaseResponse<SystemResponse>();
		if (!SessionHelper.IsUserInfoExsit()) {
			result.setCode(500);
			result.setMsg("用户未登录");
			return result;
		}
		try {
			userService.restart(request.getParameter("customerIds"),SessionHelper.GetLoginUserCode());
			SystemResponse systemResponse = new SystemResponse();
			result.setCode(200);
			result.setData(systemResponse);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setMsg("服务器错误");
		}
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/getRolelist")
	public BaseResponse<List<Role>> getRolelist(HttpServletRequest request,HttpServletResponse response){
		BaseResponse<List<Role>> result = new BaseResponse<List<Role>>();
		try {
			List<Role> dataList = userService.getRoleListAll();
			result.setCode(200);
			result.setData(dataList);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setMsg("服务器错误");
		}
		
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/getCustomList")
	public BaseResponse<List<Customer>> getCustomList(HttpServletRequest request,HttpServletResponse response){
		BaseResponse<List<Customer>> result = new BaseResponse<List<Customer>>();
		try {
			CustomerQuery query = new CustomerQuery();
			List<Customer> dataList = customerService.getList(query);
			result.setCode(200);
			result.setData(dataList);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setMsg("服务器错误");
		}
		
		return result;
	}
	
	/**
	 * 角色管理
	 * @param model
	 * @return
	 */
	@GetMapping("/roleIndex")
	public String role(Model model) {
		if (SessionHelper.IsUserInfoExsit()) {
			model.addAttribute("username", SessionHelper.GetLoginUserCode());
			model.addAttribute("userid", SessionHelper.GetLoginUserID());
		}
		return "user/role";
	}
	
	@ResponseBody
	@PostMapping(value = "/getRoleData")
	public BaseResponse<List<RoleResponse>> getRoleData(HttpServletRequest request,HttpServletResponse response){
		String roleName = request.getParameter("roleName");
		BaseResponse<List<RoleResponse>> result = new BaseResponse<List<RoleResponse>>();
		if (!SessionHelper.IsUserInfoExsit()) {
			result.setCode(500);
			result.setMsg("用户未登录");
			return result;
		}
		List<RoleResponse> list;
		try {
			list = userService.getRoleDataList(roleName);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setMsg("系统异常");
			return result;
		}
		result.setCode(200);
		result.setData(list);
		return result;
	}
	
	@ResponseBody
	@PostMapping(value = "/updateRoleMenu")
	public BaseResponse<List<RoleResponse>> updateRoleMenu(HttpServletRequest request,HttpServletResponse response){
		RoleMenuRequest roleMenuRequest = new RoleMenuRequest();
		BaseResponse<List<RoleResponse>> result = new BaseResponse<List<RoleResponse>>();
		if (SessionHelper.IsUserInfoExsit()) {
			roleMenuRequest.setModifyUser(SessionHelper.GetLoginUserCode());
		} else {
			result.setCode(500);
			result.setMsg("用户未登录");
			return result;
		}
		String type = request.getParameter("type");
		roleMenuRequest.setType(type);
		roleMenuRequest.setRoleName(request.getParameter("roleName"));
		roleMenuRequest.setFunctionID(request.getParameter("functionID"));
		try {
			userService.updateRoleMenu(roleMenuRequest);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setMsg("系统异常");
			return result;
		}
		result.setCode(200);
		return result;
	}
	
}
