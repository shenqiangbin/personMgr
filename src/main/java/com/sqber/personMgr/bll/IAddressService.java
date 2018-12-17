package com.sqber.personMgr.bll;

import com.sqber.personMgr.entity.Address;

import java.util.List;

public interface IAddressService {

    List<Address> getByParent(String regionCode);
    List<Address> getByCodes(List<String> codes);
}
