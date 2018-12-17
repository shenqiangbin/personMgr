package com.sqber.personMgr.bll.impl;

import com.sqber.personMgr.bll.IAddressService;
import com.sqber.personMgr.dal.IAddressRepository;
import com.sqber.personMgr.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService implements IAddressService {

    @Autowired
    private IAddressRepository addressRepository;

    @Override
    public List<Address> getByParent(String regionCode) {
        return addressRepository.getByParent(regionCode);
    }

    @Override
    public List<Address> getByCodes(List<String> codes) {
        if(codes == null || codes.size() == 0)
            return new ArrayList<>();

        return addressRepository.getByCodes(codes);
    }
}
