package com.sqber.personMgr.entity;

public class Address{

	private String RegionCode;
	private String Name;
	private String FullName;
	private int RegionLevel;

	public String getRegionCode() {
		return RegionCode;
	}

	public void setRegionCode(String regionCode) {
		RegionCode = regionCode;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getFullName() {
		return FullName;
	}

	public void setFullName(String fullName) {
		FullName = fullName;
	}

	public int getRegionLevel() {
		return RegionLevel;
	}

	public void setRegionLevel(int regionLevel) {
		this.RegionLevel = regionLevel;
	}
}
