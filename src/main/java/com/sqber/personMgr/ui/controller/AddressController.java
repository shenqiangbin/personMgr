package com.sqber.personMgr.ui.controller;

import com.sqber.personMgr.base.BaseResponse;
import com.sqber.personMgr.bll.IAddressService;
import com.sqber.personMgr.entity.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AddressController {

    private final Logger log = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    private IAddressService addressService;

    @GetMapping("/address/getByParent")
    public BaseResponse<List<Address>> getByParent(String code){

        BaseResponse<List<Address>> result = new BaseResponse<>();

        try{
            List<Address> list = addressService.getByParent(code);
            result.setData(list);
        }catch (Exception e) {
            result.setCode(500);
            result.setMsg("服务器错误");

            log.error(e.getMessage() + e.getStackTrace());
        }

        return result;
    }
}
