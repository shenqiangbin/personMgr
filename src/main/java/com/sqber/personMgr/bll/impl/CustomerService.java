package com.sqber.personMgr.bll.impl;

import com.sqber.personMgr.base.SessionHelper;
import com.sqber.personMgr.bll.ICustomerService;
import com.sqber.personMgr.dal.ICustomerRepository;
import com.sqber.personMgr.entity.Customer;
import com.sqber.personMgr.entity.query.CustomerQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    private ICustomerRepository customerRepository;

    @Override
    public int addCustomer(Customer model) {

        model.setStatus(1);
        model.setCreateTime(new Date());
        model.setModifyTime(new Date());

        if (SessionHelper.IsUserInfoExsit()){
            model.setCreateUser(SessionHelper.GetLoginUserCode());
            model.setModifyUser(SessionHelper.GetLoginUserCode());
        }

        return customerRepository.addCustomer(model);
    }

    @Override
    public void updateById(Customer model) {
        model.setModifyTime(new Date());
        if (SessionHelper.IsUserInfoExsit()){
            model.setModifyUser(SessionHelper.GetLoginUserCode());
        }
        customerRepository.updateById(model);
    }

    @Override
    public Customer getByID(int id) {
        return customerRepository.getByID(id);
    }

    @Override
    public List<Customer> getList(CustomerQuery query) {
        return customerRepository.getList(query);
    }

    public void removeCustomer(String ids){
        String currentUser = "";
        if (SessionHelper.IsUserInfoExsit()){
            currentUser = SessionHelper.GetLoginUserCode();
        }
        String[] s = ids.split(",");
        customerRepository.removeCustomer(currentUser,s);
    }
}
