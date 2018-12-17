package com.sqber.personMgr.ui.security;


public class SSOAuth {

	public static String CodeUrl;

	public static String appid;

	public static String accessTokenUrl;

	public static String isEnable;

	public static String appkey;
	
	public static String getAppid() {
		return appid;
	}
	public static void setAppid(String appid) {
		SSOAuth.appid = appid;
	}
	public static String getCodeUrl() {
		return CodeUrl;
	}
	public static void setCodeUrl(String codeUrl) {
		CodeUrl = codeUrl;
	}
	public static String getAccessTokenUrl() {
		return accessTokenUrl;
	}
	public static void setAccessTokenUrl(String accessTokenUrl) {
		SSOAuth.accessTokenUrl = accessTokenUrl;
	}
	public static String getIsEnable() {
		return isEnable;
	}
	public static void setIsEnable(String isEnable) {
		SSOAuth.isEnable = isEnable;
	}
	public static String getAppkey() {
		return appkey;
	}
	public static void setAppkey(String appkey) {
		SSOAuth.appkey = appkey;
	}

}
