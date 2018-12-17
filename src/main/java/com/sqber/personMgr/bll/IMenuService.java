package com.sqber.personMgr.bll;

import com.sqber.personMgr.entity.Menu;

import java.util.List;

public interface IMenuService {

    List<Menu> getListAll();
    List<Menu> getFilterList();
}
