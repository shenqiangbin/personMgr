package com.sqber.personMgr.ui.security;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;


public class SSOAccessToken {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(SSOAccessToken.class);
	
	public byte[] AccessToken(String accessToken,HttpServletRequest request, HttpServletResponse response) throws NoSuchAlgorithmException, IOException{
		try {
			//拼接url
			String date = String.valueOf(Instant.now().getEpochSecond());
			String sign = "timestamp="+date+"&appid="+SSOAuth.appid+"&appkey="+SSOAuth.appkey;
			String sha1Sign = sha1(sign).replace("-", "").toLowerCase();
			StringBuffer sb = new StringBuffer();
	        sb.append(SSOAuth.accessTokenUrl)
	                .append("?access_token=");
			String url_str = sb.toString();//获取用户认证的帐号URL
	        String ticket_url = url_str + accessToken+"&appid="+SSOAuth.appid+"&timestamp="+date+"&sign="+sha1Sign;
			//String ticket_url = url_str + accessToken; //CodeUrl 为http://sso.dev.cnki.net时打开
	        LOGGER.info("ticket_url : " + ticket_url);  
	        URL url = new URL(ticket_url);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.connect();
	        int code = connection.getResponseCode();
	        if (code != 200) {
	           throw new Exception("发生其它错误，认证服务器返回 " + code);
	        }
	        InputStream is = connection.getInputStream();
	        byte[] bt = new byte[is.available()];
	        is.read(bt);
	        is.close();
	        if (bt == null || bt.length == 0) {
	            throw new Exception("认证无效，找不到此次认证的会话信息！");
		    }
	        return bt;
		} catch (Exception e) {
			//跳转到单点登录界面  
			String uri = getUri(request);
	        String redirectUrl = buildAuthCodeUrl(uri);
	        response.sendRedirect(redirectUrl);
		}
		return null;
	}
	
	public String buildAuthCodeUrl(String uri) throws NoSuchAlgorithmException {
        StringBuffer sb = new StringBuffer();
        sb.append(SSOAuth.CodeUrl)
                .append("?appid=").append(SSOAuth.appid)
                .append("&returnurl=").append(URLEncoder.encode(uri));
        return sb.toString();
    }
	
	public static String sha1(String data) throws NoSuchAlgorithmException{  
        MessageDigest md = MessageDigest.getInstance("SHA1");  
        md.update(data.getBytes());  
        StringBuffer buf = new StringBuffer();  
        byte[] bits = md.digest();  
        for(int i=0;i<bits.length;i++){  
            int a = bits[i];  
            if(a<0) a+=256;  
            if(a<16) buf.append("0");  
            buf.append(Integer.toHexString(a));  
        }  
        return buf.toString();  
    }   
	
	//将用户信息保存到session中
	public void saveUserSession(UserDetails userDetails,HttpServletRequest request) {
		PreAuthenticatedAuthenticationToken authentication = 
  		      new PreAuthenticatedAuthenticationToken(userDetails, userDetails.getPassword(),userDetails.getAuthorities());

  		//设置authentication中details
  		authentication.setDetails(new WebAuthenticationDetails(request));

  		//存放authentication到SecurityContextHolder
  		SecurityContextHolder.getContext().setAuthentication(authentication);
  		HttpSession session = request.getSession(true);
  		//在session中存放security context,方便同一个session中控制用户的其他操作
  		session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
	}
		
	public String getUri(HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
        String servletPath = (String) request.getSession().getAttribute("servletPath");
        if(servletPath == null) {
        	servletPath = "";
        }
        return basePath + servletPath;
	   }
	
}
