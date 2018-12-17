package com.sqber.personMgr.bll.impl;

import com.sqber.personMgr.base.SessionHelper;
import com.sqber.personMgr.bll.IMenuService;
import com.sqber.personMgr.dal.IBdMenuRepository;
import com.sqber.personMgr.entity.Menu;
import com.sqber.personMgr.enums.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService implements IMenuService {

    @Autowired
    private IBdMenuRepository menuRepository ;


    @Override
    public List<Menu> getListAll() {
        return menuRepository.getListAll();
    }

    public List<Menu> getFilterList(){
        List<Menu> list = getMenuby(SessionHelper.getRoleId());
        return list;
    }

    private List<Menu> getMenuby(RoleType type){
        //List<Menu> list = new ArrayList<>();

        List<Menu> list = this.getListAll();

        list = list.stream()
                .filter(m->m.getRoles().contains(type.getValue()+","))
                .collect(Collectors.toList());

        return list;
//        list.add(new Menu("/Customer/CustomerMgr","客户列表管理","#icon-list"));
//        list.add(new Menu("/BaseMap/BaseMapList","底图管理","#icon-pic"));
//        list.add(new Menu("/SensorManager/SensorList","测点管理","#icon-ceshi"));
//        list.add(new Menu("/dataManager/index","数据管理","#icon-date"));
//        list.add(new Menu("/dataStatistics/index","数据统计","#icon-baobiao"));
//        list.add(new Menu("/monitorManager/monitor","报警管理","#icon-fire"));
//        list.add(new Menu("#","数据库管理","#icon-sql"));
//        if(!type.equals(RoleType.User)){
//            list.add(new Menu("/user/index","用户管理","#icon-manager"));
//            //list.add(new Menu("/user/index","权限管理","#icon-manager"));
//        }


    }
}
