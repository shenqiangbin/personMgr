package com.sqber.personMgr.dal;

import java.util.List;

import com.sqber.personMgr.entity.Menu;
import com.sqber.personMgr.entity.Role;

public interface IBdMenuRepository {
	
	List<Menu> getListAll();
	
	Role getRoleByName(Menu menu);
	
}
