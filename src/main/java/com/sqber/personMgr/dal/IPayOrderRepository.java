package com.sqber.personMgr.dal;

import com.sqber.personMgr.entity.PayOrder;
import org.apache.ibatis.annotations.Param;

public interface IPayOrderRepository {

    int insert(PayOrder model);

    void updateById(PayOrder model);

    void updateState(@Param("id") int id, @Param("state") int state);

    void updateStateByOrderNo(@Param("orderNo") String orderNo, @Param("state") int state);

    PayOrder getByOrderNo(@Param("orderNo") String orderNo);
}
