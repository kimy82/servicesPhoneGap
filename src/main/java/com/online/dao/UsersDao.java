package com.online.dao;

import java.util.List;

import com.online.model.UserRole;
import com.online.model.Users;


public interface UsersDao {
	
	void save(Users user) ;
	
	void update(Users user);
	
	void delete(Users user);
	
	Users findByUsername(String username);
	
	List<Users> getAll();
	
	Users load( Long id );
	
	void saveRole(UserRole usuRole);
	
	UserRole loadRole( Long id );
	
}
