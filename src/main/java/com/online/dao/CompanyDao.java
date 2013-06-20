package com.online.dao;

import java.util.List;

import com.online.model.Company;


public interface CompanyDao {
	
	void save(Company company) ;
	
	void update(Company company);
	
	void delete(Company company);
	
	List<Company> getAll();
	
	Company load( Integer id );
	
}
