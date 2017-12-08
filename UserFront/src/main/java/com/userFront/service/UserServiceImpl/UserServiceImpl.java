package com.userFront.service.UserServiceImpl;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.userFront.dao.RoleDao;
import com.userFront.dao.UserDao;
import com.userFront.domain.User;
import com.userFront.domain.security.UserRole;
import com.userFront.service.AccountService;
import com.userFront.service.UserService;

//lec017AddUserServiceAndUserDAO make springBt reg the bean in DB
@Service
//@Component
//020 can be used at meth or class lev; without this some db transactions would not be supported
@Transactional
public class UserServiceImpl implements UserService{
	
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	
	//017 link this class to the userDao using autowiring the DI bean, by just asking for it 
	@Autowired
	private UserDao userDao;
	
	@Autowired 
	RoleDao roleDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
    private AccountService accountService;

	
	public void save(User user) {
		userDao.save(user);
	}
	
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}
	
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}
	
	
	public User createUser(User user, Set<UserRole> userRoles) {
		//020 check if user exists
		User localUser = userDao.findByUsername(user.getUsername());
		
		if(localUser != null) {
			LOG.info("User with username {} al ready exists. Nothing will be done. ", user.getUsername());
		}else {
			//020 need pass to be of Bcrypt type encoding instead of original str of signup/stored in DB so that user can sign into website after signing up;
			 //DB also needs to store Bcrypt, not userStr 
			String encryptedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encryptedPassword);
			
			//also assign user roles for DB persistence
			for(UserRole ur: userRoles) {
				roleDao.save(ur.getRole());
			}
			
			user.getUserRoles().addAll(userRoles);
			
			
			
			user.setPrimaryAccount(accountService.createPrimaryAccount());
			user.setSavingsAccount(accountService.createSavingsAccount());
			
			localUser = userDao.save(user);
		}
		
		return localUser;
		
	}
	
	public boolean checkUserExists(String username, String email) {
		if(checkUsernameExists(username)|| checkEmailExists(email)) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean checkEmailExists(String email) {
		
		if(null != findByEmail(email)) {
			return true;
		}
		
		return false;
	}

	public boolean checkUsernameExists(String username) {
		if(null != findByUsername(username)) {
			return true;
		}
		
		return false;
	}

}
