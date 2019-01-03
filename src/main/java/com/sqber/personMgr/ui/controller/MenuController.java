package com.sqber.personMgr.ui.controller;

import com.sqber.personMgr.base.BaseResponse;
import com.sqber.personMgr.bll.IMenuService;
import com.sqber.personMgr.entity.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MenuController {

    private static final Logger log = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private IMenuService menuService;

    @ResponseBody
    @GetMapping("menu/getList")
    public BaseResponse<List<Menu>> getList(){

        BaseResponse<List<Menu>> result = new BaseResponse<>();
        try {
            List<Menu> list = menuService.getFilterList();
            result.setData(list);

        } catch (Exception e) {
            result.setCode(500);       result.setMsg("服务器错误");

            log.error(e.getMessage() + e.getStackTrace());
        }

        return result;

    }

    @GetMapping("menu/first")
    public String goMenu(){
        List<Menu> list = menuService.getFilterList();
        if(list!=null && list.size()>0)
        {
            return "redirect:" + list.get(0).getMenuURL();
        }
        return "redirect:/nothing";
    }

}
