package com.sqber.personMgr.ui.security;

import org.springframework.security.core.AuthenticationException;

public class ValidateCodeException extends AuthenticationException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8170614544681666345L;

	public ValidateCodeException(String msg) {
		super(msg);
	}
}
