package com.sqber.personMgr.ui.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sqber.personMgr.bll.ICodeService;

@Component
public class ValidateCodeFilter extends OncePerRequestFilter implements Filter{
   		
	@Autowired
	private ICodeService codeService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		//System.out.println(request.getRequestURI() + " --- " + request.getMethod());
		
		if (request.getRequestURI().contains("login") && request.getMethod().equals("POST")) {
			try {
				validate(request);
			} catch (ValidateCodeException e) {
				SimpleUrlAuthenticationFailureHandler handler = new SimpleUrlAuthenticationFailureHandler();
				handler.setDefaultFailureUrl("/login?error=true");
				handler.onAuthenticationFailure(request, response, e);
				return;
			}
		}
		
		filterChain.doFilter(request, response);

	}

	private void validate(HttpServletRequest request) {
		String code = request.getParameter("code");
		String time = request.getParameter("time");
		String md5 = request.getParameter("md5");
		String username = request.getParameter("username");
		
		boolean isOk = codeService.checkCode(code, time, md5);
		
		if(!isOk)
			throw new ValidateCodeException("验证码错误");		
	}

}

