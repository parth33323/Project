package com.userFront.dao;

import org.springframework.data.repository.CrudRepository;

import com.userFront.domain.User;
//017 the dao handles persistence of the model;  crudRepo interface user as its class and long as the PK; crudRepo has del and find and save (create and update); sprinBt will gen entity class based on this UserDao interface
public interface UserDao extends CrudRepository<User,Long>{

	//017 mappers; retreive uName or Email to retrieve inst of user Entity; the naming 'findBy' helps sBt retrieve user because it assume that the user class has userNm and email fields and it ignore the UserNm case in the dao, and it will gen mappers during initialization to be used for retrieving the user obj
	User findByUsername(String username);
	User findByEmail(String email);
}
