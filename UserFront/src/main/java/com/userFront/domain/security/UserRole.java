package com.userFront.domain.security;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.userFront.domain.User;

@Entity
//019 specify table nm
@Table(name = "user_role")
public class UserRole {
	
	@Id
	
	//auto gen the userRoleId
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userRoleId; 
	
	public UserRole (User user, Role role){
		this.user = user;
		this.role = role;
	}
	
	//019 ??  
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name  = "role_id")
	private Role role;

	public long getUserRoleId() {
		return userRoleId;
	}


	public void setUserRoleId(long userRoleId) {
		this.userRoleId = userRoleId;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}
	
	
	
	
	
}