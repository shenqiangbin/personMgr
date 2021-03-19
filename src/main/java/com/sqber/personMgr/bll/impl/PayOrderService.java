package com.sqber.personMgr.bll.impl;

import com.alibaba.fastjson.JSONArray;
import com.sqber.personMgr.base.SessionHelper;
import com.sqber.personMgr.bll.IPayOrderService;
import com.sqber.personMgr.bll.IUserService;
import com.sqber.personMgr.dal.*;
import com.sqber.personMgr.entity.*;
import com.sqber.personMgr.entity.query.RoleMenuRequest;
import com.sqber.personMgr.entity.query.RoleResponse;
import com.sqber.personMgr.entity.query.RoleResponseItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.util.*;

@Service
public class PayOrderService implements IPayOrderService {

    @Autowired
    private IPayOrderRepository payOrderRepository;


    @Override
    public int insert(PayOrder model) {
        model.setCreateTime(new Date());
        model.setCreateUser(SessionHelper.GetLoginUserCode());
        model.setStatus(1);
        return this.payOrderRepository.insert(model);
    }

    @Override
    public void updateById(PayOrder model) {
        model.setModifyTime(new Date());
        model.setModifyUser(SessionHelper.GetLoginUserCode());
        this.payOrderRepository.updateById(model);
    }

    @Override
    public void completeOrder(String orderNo){
        this.payOrderRepository.updateStateByOrderNo(orderNo,PayOrder.HAS_PAYED);
    }

    @Override
    public void updateState(int id, int state) {
        this.payOrderRepository.updateState(id, state);
    }

    @Override
    public PayOrder getByOrderNo(String orderNo) {
        return this.payOrderRepository.getByOrderNo(orderNo);
    }
}
