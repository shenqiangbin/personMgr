package com.sqber.personMgr.ui.security;

import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.sqber.personMgr.base.ActionRolesReader;


@Component
public class MyInterceptor extends HandlerInterceptorAdapter{
	
	@Autowired
	public ActionRolesReader actionRolesReader;
	
	public static final Logger LOGGER = LoggerFactory.getLogger(MyInterceptor.class);

	@Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(response.getStatus()!=200){ //如果单点登录调取令牌接口出现问题则跳转至单点登录界面
			String uri = getUri(request);
	        String redirectUrl = buildAuthCodeUrl(uri);
	        response.sendRedirect(redirectUrl);
        }  
		if ((SecurityContextHolder.getContext() != null ) 
				&& (SecurityContextHolder.getContext().getAuthentication() != null) 
				&& (SecurityContextHolder.getContext().getAuthentication().getName() != null) 
				&& (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"))) {
		  return true;
		  } else {
			  String servletPath = request.getRequestURI();
			  HttpSession session = request.getSession();
			  if(servletPath.equals("/models") || servletPath.equals("/reports")) {
				  session.setAttribute("servletPath", servletPath);
			  }
			  //判断哪些页面不用登录就可以访问
			  ConfigAttribute c;
			  String needRole;
		      AntPathRequestMatcher matcher;
			  String resUrl;
			  Map<String, String> map = actionRolesReader.GetActionRolesConfig();
			  Collection<ConfigAttribute> array = new ArrayList<>();
			  for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext();) {
				resUrl = iter.next();
				matcher = new AntPathRequestMatcher(resUrl);
				if (matcher.matches(request)) {
					array.add(new SecurityConfig(map.get(resUrl)));
				}
			  }
			
			  for (Iterator<ConfigAttribute> iter = array.iterator(); iter.hasNext();) {
				c = iter.next();
				needRole = c.getAttribute();
				if ("ALL".equals(needRole))
				{
					//跳转到单点登录界面  
					String uri = getUri(request);
			        String redirectUrl = buildAuthCodeUrl(uri);
			        response.sendRedirect(redirectUrl);
				}
			  }
	       return true;
		}
	}
	
	private String buildAuthCodeUrl(String uri) throws NoSuchAlgorithmException {
        StringBuffer sb = new StringBuffer();
        sb.append(SSOAuth.CodeUrl)
                .append("?appid=").append(SSOAuth.appid)
                .append("&returnurl=").append(URLEncoder.encode(uri));
        return sb.toString();
    }
	
	private String getUri(HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
        String servletPath = request.getServletPath();
        return basePath + servletPath;
    }

}
