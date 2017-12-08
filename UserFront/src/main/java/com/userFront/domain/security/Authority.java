package com.userFront.domain.security;

import org.springframework.security.core.GrantedAuthority;


//019 class defines auth with str to rep auth content
public class Authority implements GrantedAuthority{
	
	private final String authority;
	
	public Authority(String authority) {
		this.authority  = authority;
	}
	
	

	@Override
	public String getAuthority() {
		return authority;
	}
	

}
