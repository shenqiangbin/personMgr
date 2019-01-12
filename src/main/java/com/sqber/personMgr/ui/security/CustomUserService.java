package com.sqber.personMgr.ui.security;

import java.util.ArrayList;
import java.util.List;

import com.sqber.personMgr.dal.IBdRoleRepository;
import com.sqber.personMgr.dal.IBdUserRoleRepository;
import com.sqber.personMgr.dal.ICustomerRepository;
import com.sqber.personMgr.dal.IUserRepository;
import com.sqber.personMgr.entity.Role;
import com.sqber.personMgr.entity.UserRole;
import com.sqber.personMgr.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sqber.personMgr.entity.LoginUserInfo;

@Service
public class CustomUserService implements UserDetailsService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IBdRoleRepository roleRepository;
	
	@Autowired
	private ICustomerRepository customerRepository;

	@Autowired
	private IBdUserRoleRepository userRoleRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User userinfo = userRepository.getByName(username);
		if (userinfo != null) {
			List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("USER");
			// 1：此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
			grantedAuthorities.add(grantedAuthority);
			
			UserRole urole = new UserRole();
			urole.setUserID(userinfo.getUserId());
			List<UserRole> userRoleList = userRoleRepository.getByUserId(urole);
			if(null != userRoleList && userRoleList.size() > 0) {
				for (UserRole userRole : userRoleList) {
					Role role = new Role();
					role.setRoleID(userRole.getRoleID());
					Role bdRole = roleRepository.getRoleById(role);
					GrantedAuthority grantedAuthoritys = new SimpleGrantedAuthority(bdRole.getRoleCode());
					grantedAuthorities.add(grantedAuthoritys);
				}
				
			}

			LoginUserInfo user = new LoginUserInfo(userinfo.getUserCode(), userinfo.getPassword() == null ? "" : userinfo.getPassword(),
					grantedAuthorities);
			user.setUserID(userinfo.getUserId());
			user.setUserCode(userinfo.getUserCode());
			user.setDisplayName(userinfo.getUserName());

			/*user.setSsoUserID(userinfo.getSsoUserID());*/
			if(userRoleList.size()==0)
				throw new ValidateCodeException("没有角色权限");

			user.setRoleId(userRoleList.get(0).getRoleID());
			user.setCustomerId(userRepository.getByID(user.getUserID()).getCustomerID());
			return user;
		} else {
			throw new UsernameNotFoundException("admin: " + username + " do not exist!");
		}
	}
	
}
