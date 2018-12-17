package com.sqber.personMgr.bll;

import com.sqber.personMgr.entity.Customer;
import com.sqber.personMgr.entity.query.CustomerQuery;

import java.util.List;

public interface IProjectService {

    int addCustomer(Customer model);

    void updateById(Customer model);

    Customer getByID(int id);

    List<Customer> getList(CustomerQuery query);

    void removeCustomer(String ids);
}
