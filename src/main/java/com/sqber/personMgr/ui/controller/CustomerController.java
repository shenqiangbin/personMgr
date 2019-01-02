package com.sqber.personMgr.ui.controller;

import com.sqber.personMgr.base.BaseResponse;
import com.sqber.personMgr.base.PagedResponse;
import com.sqber.personMgr.base.SessionHelper;
import com.sqber.personMgr.bll.IAddressService;
import com.sqber.personMgr.bll.ICustomerService;
import com.sqber.personMgr.entity.Address;
import com.sqber.personMgr.entity.Customer;
import com.sqber.personMgr.entity.CustomerListItem;
import com.sqber.personMgr.entity.query.CustomerQuery;
import com.sqber.personMgr.enums.RoleType;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IAddressService addressService;

    @GetMapping("Customer/CustomerList")
    public String customerList(){
        return "Customer/customerList";
    }

    @ResponseBody
    @GetMapping("Customer/GetList")
    public BaseResponse<PagedResponse<Customer>> getList(int currentPage, String name) {

        BaseResponse<PagedResponse<Customer>> result = new BaseResponse<>();

        try {

            int pageSize = 10;

            CustomerQuery query = new CustomerQuery();
            query.setCustomerName(name);

            PageHelper.startPage(currentPage, pageSize);
            List<Customer> list = customerService.getList(query);

            PagedResponse<Customer> pagedResponse = new PagedResponse<>(list,currentPage,pageSize);

            result.setData(pagedResponse);

        } catch (Exception e) {
            result.setCode(500);
            result.setMsg("服务器错误");

            log.error(e.getMessage() + e.getStackTrace());
        }

        return result;
    }

    @ResponseBody
    @GetMapping("Customer/GetListItems")
    public BaseResponse<PagedResponse<CustomerListItem>> getListItems(int currentPage, String name) {

        BaseResponse<PagedResponse<CustomerListItem>> result = new BaseResponse<>();

        try {

            int pageSize = 10;

            CustomerQuery query = new CustomerQuery();
            query.setCustomerName(name);

            if(SessionHelper.getRoleId().equals(RoleType.User))
            {
                query.setCustomerId(SessionHelper.getCustomerId());
            }

            PageHelper.startPage(currentPage, pageSize);
            List<Customer> list = customerService.getList(query);

            PageInfo<Customer> pageInfo = new PageInfo<>(list);

            long total= pageInfo.getTotal();
            int pages = pageInfo.getPages();

            List<Address> address =  getAddress(list);


            List<CustomerListItem> listItems = new ArrayList<>();
            for (Customer item : list) {
                CustomerListItem listItem = new CustomerListItem();
                listItem.setModel(item);
                listItem.setRegionDesc(getName(address,item.getRegion()));
                listItems.add(listItem);
            }

            PagedResponse<CustomerListItem> pagedResponse = new PagedResponse<>();

            pagedResponse.setCurrentPage(currentPage);
            pagedResponse.setPageSize(pageSize);

            pagedResponse.setTotalCount(total);
            pagedResponse.setTotalPage(pages);

            pagedResponse.setList(listItems);

            result.setData(pagedResponse);

        } catch (Exception e) {
            result.setCode(500);
            result.setMsg("服务器错误");

            log.error(e.getMessage() + e.getStackTrace());
        }

        return result;
    }

    private List<Address> getAddress(List<Customer> list){
        List<String> codes = new ArrayList<>();
        for(Customer item : list){
            if(StringUtils.isEmpty(item.getRegion()))
                continue;
            codes.add(item.getRegion());
        }

        return addressService.getByCodes(codes);
    }


    private  int getSensorNum(List<Map<String,Object>> map,int customerId){
        for (Map<String,Object> item:map) {
            if(item.get("CustomerID").equals(customerId))
                return Integer.valueOf(item.get("Count").toString());
        }
        return  0;
    }

    private String getName(List<Address> list,String code){
        for(Address item : list)
        {
            if(item.getRegionCode().equals(code))
                return item.getName();
        }
        return "";
    }

    @GetMapping("Customer/CustomerMgr")
    public String customerMgr(){
        return "Customer/customerMgr";
    }

    @ResponseBody
    @PostMapping("Customer/removeCustomer")
    public BaseResponse<Boolean> removeCustomer(String ids){

        BaseResponse<Boolean> result = new BaseResponse<>();

        try{

            customerService.removeCustomer(ids);

        }catch (Exception e) {
            result.setCode(500);
            result.setMsg("服务器错误");

            log.error(e.getMessage() + e.getStackTrace());
        }

        return result;
    }

    @GetMapping("Customer/CustomerAdd")
    public String customerAdd(){
        return "Customer/customerAdd";
    }

    @ResponseBody
    @GetMapping("Customer/GetById")
    public BaseResponse<Customer> GetById(int id) {

        BaseResponse<Customer> result = new BaseResponse<>();

        try {
            Customer model = customerService.getByID(id);
            result.setData(model);

        } catch (Exception e) {
            result.setCode(500);
            result.setMsg("服务器错误");

            log.error(e.getMessage() + e.getStackTrace());
        }

        return result;
    }

    @ResponseBody
    @PostMapping("Customer/saveCustomer")
    public BaseResponse<String> saveCustomer(Customer model){

        BaseResponse<String> result = new BaseResponse<>();

        try{
            System.out.println(model.getCustomerID());
            if(model.getCustomerID()!=null && model.getCustomerID()!=0){
                Customer dbModel = customerService.getByID(model.getCustomerID());

                dbModel.setCustomerName(model.getCustomerName());
                dbModel.setRegion(model.getRegion());
                dbModel.setNote(model.getNote());
                dbModel.setLng(model.getLng());
                dbModel.setLat(model.getLat());
                dbModel.setSecurityLevel(model.getSecurityLevel());
                dbModel.setMaxVal(model.getMaxVal());

                customerService.updateById(dbModel);
            }else{

                customerService.addCustomer(model);
                result.setData(String.valueOf(model.getCustomerID()));
            }

        }catch (Exception e) {
            result.setCode(500);
            result.setMsg("服务器错误");

            log.error(e.getMessage() + e.getStackTrace());
        }

        return result;
    }

    @ResponseBody
    @GetMapping("Customer/test")
    public void test(){
        Customer p = new Customer();
        p.setCustomerName("abc");
        customerService.addCustomer(p);
    }

}
