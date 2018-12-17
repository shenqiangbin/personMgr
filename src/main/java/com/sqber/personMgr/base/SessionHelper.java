package com.sqber.personMgr.base;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.sqber.personMgr.entity.LoginUserInfo;
import com.sqber.personMgr.enums.RoleType;

public class SessionHelper {

    public static String GetLoginUserCode() {
        String result = "";

        try {
            if (IsUserInfoExsit())
            {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                LoginUserInfo userInfo = (LoginUserInfo)auth.getPrincipal();
                result = userInfo.getUserCode(); // 主体名，即登录用户名
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    public static String GetLoginUserName() {
        String result = "";

        try {
            if (IsUserInfoExsit())
            {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                result = auth.getName(); // 主体名，即登录用户名
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    public static int GetLoginUserID() {
        int result = 0;

        try {
            if (IsUserInfoExsit())
            {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                LoginUserInfo userInfo = (LoginUserInfo)auth.getPrincipal();
                result = userInfo.getUserID(); // 主体名，即登录用户名
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }


    public static boolean IsUserInfoExsit()
    {
        boolean result = false;

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth.getClass() != AnonymousAuthenticationToken.class)
            {
                result = auth.isAuthenticated();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;

    }

    /**
     * 获取用户权限列表
     * @return
     */
    public static List<String> getRoleList(){
        List<String> dataList = new ArrayList<String>();
        try {
            if (IsUserInfoExsit())
            {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                Object obj = auth.getAuthorities();
                if(null != obj) {
                    dataList = (List<String>) obj;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataList;
    }
    
    /**
     * 获取用户角色id
     * @return
     */
    public static RoleType getRoleId(){
    	try {
    		 if (IsUserInfoExsit())
             {
                 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                 LoginUserInfo userInfo = (LoginUserInfo)auth.getPrincipal();
                 if(null != userInfo) {
                	 Integer roleId = userInfo.getRoleId();
                	 if(1 == roleId) {
                		 return RoleType.Admin;
                	 }else if(2 == roleId) {
                		 return RoleType.DataManager;
                	 }else if(3 == roleId) {
                		 return RoleType.User;
                	 }
                 }
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return null;
    }
    
    /**
     * 获取用户公司id
     * @return
     */
    public static Integer getCustomerId(){
    	try {
    		 if (IsUserInfoExsit())
             {
                 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                 LoginUserInfo userInfo = (LoginUserInfo)auth.getPrincipal();
                 if(null != userInfo) {
                	 return userInfo.getCustomerId();
                 }
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return null;
    }
    
}
