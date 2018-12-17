package com.sqber.personMgr.dal;

import com.sqber.personMgr.entity.Customer;
import com.sqber.personMgr.entity.query.CustomerQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ICustomerRepository {

	int addCustomer(Customer model);

	void updateById(Customer model);

	Customer getByID(int id);
	
	List<Customer> getList(CustomerQuery query);

	void removeCustomer(@Param("currentUser")String currentUser, @Param("ids")String[] ids);

}
