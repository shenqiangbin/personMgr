package com.sqber.personMgr.dal;

import com.sqber.personMgr.entity.Address;

import java.util.List;

public interface IAddressRepository {

	List<Address> getByParent(String regionCode);

    List<Address> getByCodes(List<String> codes);

    //void removeCustomer(@Param("currentUser") String currentUser, @Param("ids") String[] ids);

}
