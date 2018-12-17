package com.sqber.personMgr.entity;

public class CustomerListItem {

    private Customer model;

    private String regionDesc;

    private int sensorNum;

    public int getSensorNum() {
        return sensorNum;
    }

    public void setSensorNum(int sensorNum) {
        this.sensorNum = sensorNum;
    }

    public Customer getModel() {
        return model;
    }

    public void setModel(Customer model) {
        this.model = model;
    }

    public String getRegionDesc() {
        return regionDesc;
    }

    public void setRegionDesc(String regionDesc) {
        this.regionDesc = regionDesc;
    }
}
