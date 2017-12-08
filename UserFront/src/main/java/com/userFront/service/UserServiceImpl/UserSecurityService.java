package com.userFront.service.UserServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.userFront.dao.UserDao;
import com.userFront.domain.User;

//019 anno to reg service nm
@Service
//019 UserDetailsService used in sec config as a user data service auth provider
public class UserSecurityService implements UserDetailsService{
	
	private static final Logger LOG = LoggerFactory.getLogger(UserSecurityService.class);
	
	@Autowired
	private UserDao userDao;
	
	//019 try to find and return username from login pg  in DB
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username);
		if(null == user) {
			LOG.warn("Username {} not found", username);
			throw new UsernameNotFoundException("Username "+ username + " not found");
		}
		//019 returning error bc method expects userDetails in return
		return user;
	}

}
