package com.sqber.personMgr.entity;

public class Customer extends BaseEntity{

	private Integer CustomerID;
	private String CustomerName;
	private String Img;
	private String Region;

	private double Lng;
	private double Lat;

	private String SecurityLevel;
	private String MaxVal;
	private String Note;

	public String getRegion() {
		return Region;
	}

	public void setRegion(String region) {
		Region = region;
	}

	public String getNote() {
		return Note;
	}

	public void setNote(String note) {
		Note = note;
	}

	public Integer getCustomerID() {
		return CustomerID;
	}

	public void setCustomerID(Integer customerID) {
		CustomerID = customerID;
	}

	public String getCustomerName() {
		return CustomerName;
	}

	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}

	public double getLng() {
		return Lng;
	}

	public void setLng(double lng) {
		Lng = lng;
	}

	public double getLat() {
		return Lat;
	}

	public void setLat(double lat) {
		Lat = lat;
	}

	public String getImg() {
		return Img;
	}

	public void setImg(String img) {
		Img = img;
	}

	public String getSecurityLevel() {
		return SecurityLevel;
	}

	public void setSecurityLevel(String securityLevel) {
		SecurityLevel = securityLevel;
	}

	public String getMaxVal() {
		return MaxVal;
	}

	public void setMaxVal(String maxVal) {
		MaxVal = maxVal;
	}

}
