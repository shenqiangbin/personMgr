package com.sqber.personMgr.bll;

import com.sqber.personMgr.entity.PayOrder;

public interface IPayOrderService {

    int insert(PayOrder model);
    void updateById(PayOrder model);
    void completeOrder(String orderNo);
    void updateState(int id, int state);
    PayOrder getByOrderNo(String orderNo);
}
